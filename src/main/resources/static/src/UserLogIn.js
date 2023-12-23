import React, { useState } from 'react';
import { Link } from 'react-router-dom'
import './UserRegistration.css';

function UserLogIn() {

    const [error, setError] = useState(null);

    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch(`http://localhost:8080/users/verify/${formData.email}?password=${formData.password}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            }).then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
                .then((data) => {
                    if (data === true) {
                        console.log('Log-In successful');
                        return (
                            <div>
                                <Link to="/home">Continue</Link>
                            </div>
                        )
                    } else {
                        setError('Log-In failed');
                        console.error('Error:', error);
                    }
                })
                .catch((error) => {
                    setError('Request failed');
                    console.error('Error:', error);
                });

        } catch (error) {
            setError('Log-In failed');
            console.error('Error:', error);
        }
    };

    return (
        <div className="user-login-form">
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Register</button>
            </form>
            {error && <div className="error-message">{error}</div>}
            <div>
                <Link to="/register">Create new user</Link>
            </div>
        </div>
    );
}

export default UserLogIn;
