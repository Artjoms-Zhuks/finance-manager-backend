import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [stats, setStats] = useState({});
  const [uniqueCategories, setUniqueCategories] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');


  const [description, setDescription] = useState('');
  const [amount, setAmount] = useState('');
  const [category, setCategory] = useState('');
  const [editingId, setEditingId] = useState(null);
  const [selectedPeriod, setSelectedPeriod] = useState('total');

  const periodNames = {
    total: "Overall balance", today: "Today's expenses",
    lastDay: "Last 24 hours", thisWeek: "This week", thisMonth: "This month"
  };

  useEffect(() => { fetchData(); }, []);

  const fetchData = async () => {
    try {
      const [statsRes, catRes, transRes] = await Promise.all([
        axios.get('http://localhost:8080/api/statistics/balance'),
        axios.get('http://localhost:8080/api/statistics/categories/unique'),
        axios.get('http://localhost:8080/api/transactions')
      ]);
      setStats(statsRes.data);
      setUniqueCategories(catRes.data);
      setTransactions(transRes.data);
    } catch (error) { console.error("Error:", error); }
  };

  const handleSearch = async (query) => {
    setSearchQuery(query);
    const res = await axios.get(`http://localhost:8080/api/transactions/search?query=${query}`);
    setTransactions(res.data);
  };

  // CREATE or UPDATE
  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = { description, amount: parseFloat(amount), category };
    try {
      if (editingId) {
        await axios.put(`http://localhost:8080/api/transactions/${editingId}`, data);
        setEditingId(null);
      } else {
        await axios.post('http://localhost:8080/api/transactions', data);
      }
      setDescription(''); setAmount(''); setCategory('');
      fetchData();
    } catch (error) { alert("Error saving transaction"); }
  };

  // DELETE
  const handleDelete = async (id) => {
    if (window.confirm("Delete this transaction?")) {
      await axios.delete(`http://localhost:8080/api/transactions/${id}`);
      fetchData();
    }
  };

  // PREPARATION FOR EDITING
  const startEdit = (t) => {
    setEditingId(t.id);
    setDescription(t.description);
    setAmount(t.amount);
    setCategory(t.category);
    window.scrollTo(0, 0);
  };

  return (
    <div className="App">
      <h1>Finance Manager</h1>

      <div className="period-selector">
        {Object.keys(periodNames).map(p => (
          <button key={p} className={selectedPeriod === p ? 'active' : ''} onClick={() => setSelectedPeriod(p)}>
            {periodNames[p]}
          </button>
        ))}
      </div>

      <div className="balance-card main-display">
        <h2>{periodNames[selectedPeriod]}</h2>
        <p className="amount">{stats[selectedPeriod] || 0} $</p>
      </div>

      <div className="main-content">
        <form onSubmit={handleSubmit} className="transaction-form">
          <h3>{editingId ? "Update Transaction" : "Add Transaction"}</h3>
          <input type="text" placeholder="Description" value={description} onChange={e => setDescription(e.target.value)} required />
          <input type="number" placeholder="Amount" value={amount} onChange={e => setAmount(e.target.value)} required />
          <input type="text" placeholder="Category" list="categories" value={category} onChange={e => setCategory(e.target.value)} required />
          <datalist id="categories">
            {uniqueCategories.map(cat => <option key={cat} value={cat} />)}
          </datalist>
          <button type="submit" className={editingId ? "update-btn" : ""}>
            {editingId ? "Save Changes" : "Add"}
          </button>
          {editingId && <button type="button" onClick={() => { setEditingId(null); setDescription(''); setAmount(''); setCategory(''); }}>Cancel</button>}
        </form>

        <div className="history-section">
          <h3>History</h3>
          <input type="text" className="search-input" placeholder="Search..." onChange={e => handleSearch(e.target.value)} />
          <div className="transaction-list">
            {transactions.map(t => (
              <div key={t.id} className="transaction-item">
                <div onClick={() => startEdit(t)} style={{ cursor: 'pointer' }}>
                  <span>{t.description} <b>({t.category})</b></span>
                  <br />
                </div>
                <div className="item-right">
                  <span className="t-amount">-{t.amount} EUR</span>
                  <button className="delete-btn" onClick={() => handleDelete(t.id)}>🗑</button>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;