function SearchBar({ value, onChange, placeholder = 'Pretraži...' }) {
    return (
        <input
            className="search"
            type="text"
            value={value}
            onChange={e => onChange(e.target.value)}
            placeholder={placeholder}
        />
    )
}

export default SearchBar