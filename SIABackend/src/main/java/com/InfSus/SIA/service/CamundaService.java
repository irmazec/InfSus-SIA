package com.InfSus.SIA.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CamundaService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public String startProcess(Long rezervacijaId, String gostEmail) {
        Map<String, Object> data = new HashMap<>();
        data.put("rezervacijaId", rezervacijaId);
        data.put("guestEmail",    gostEmail);

        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("pregled-uplata-process", data);
        return processInstance.getId();
    }

    public List<Map<String, Object>> getTasksForUser() {
        return taskService.createTaskQuery()
                .taskAssignee("iznajmljivac")
                .list()
                .stream()
                .map(t -> {
                    Map<String, Object> task = new HashMap<>();
                    task.put("taskId",   t.getId());
                    task.put("taskName", t.getName());
                    task.put("taskDefinitionKey", t.getTaskDefinitionKey());
                    task.put("vars",     taskService.getVariables(t.getId()));
                    return task;
                })
                .collect(Collectors.toList());
    }

    public void registerPayment(String taskId, Double iznos, Boolean podaciIspravni, Boolean uplataOtplacena){
        Map<String, Object> data = new HashMap<>();
        data.put("iznos", iznos);
        data.put("podaciIspravni", podaciIspravni);
        data.put("uplataOtplacena", uplataOtplacena);

        if (iznos <= 0) {
            throw new RuntimeException("Iznos mora biti veći od 0!");
        }

        taskService.complete(taskId, data);
    }

    public void correctData(String taskId, Map<String, Object> data){
        if (data.containsKey("iznos")){
            if (Double.parseDouble(data.get("iznos").toString()) <= 0.0){
                throw new RuntimeException("Iznos mora biti veći od 0!");
            }
        }
        data.put("podaciIspravni", true);
        taskService.complete(taskId, data);
    }

    public void changeStatusToPayed(String taskId){
        Map<String, Object> data = new HashMap<>();
        data.put("status", "PLACENO");
        taskService.complete(taskId, data);
    }

    public void deletePaymentAndReservation(String taskId){
        taskService.complete(taskId);
    }

    public Map<String, Object> getProcessStatus(String processInstanceId){
        return runtimeService.getVariables(processInstanceId);
    }

    public void payReservationFully(String processInstanceId) {
        runtimeService.setVariable(processInstanceId, "uplataOtplacena", true);
        runtimeService.createSignalEvent("uplata-primljena")
                .executionId(
                        runtimeService.createExecutionQuery()
                                .processInstanceId(processInstanceId)
                                .signalEventSubscriptionName("uplata-primljena")
                                .singleResult()
                                .getId()
                )
                .send();
    }
}
