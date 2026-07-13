function Dashboard({ questions }) {

    const total = questions.length;

    const analyzed = questions.filter(
        q => q.status === "COMPLETED"
    ).length;

    const pending = questions.filter(
        q => q.status === "PENDING"
    ).length;

    return (

        <div className="dashboard">

            <div className="dashboard-card">

                <span className="dashboard-icon">📚</span>

                <div>

                    <h3>Total</h3>

                    <p>{total}</p>

                </div>

            </div>

            <div className="dashboard-card">

                <span className="dashboard-icon">🤖</span>

                <div>

                    <h3>Analisadas</h3>

                    <p>{analyzed}</p>

                </div>

            </div>

            <div className="dashboard-card">

                <span className="dashboard-icon">⏳</span>

                <div>

                    <h3>Pendentes</h3>

                    <p>{pending}</p>

                </div>

            </div>

        </div>

    );

}

export default Dashboard;