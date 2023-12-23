import React, { useState } from 'react';
import './UserRegistration.css';

function UserRegistrationForm() {

    const [error, setError] = useState(null);

    const [formData, setFormData] = useState({
        username: '',
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
            const response = await fetch('http://localhost:8080/users/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                // Registration successful, you can redirect or show a success message
                console.log('Registration successful');
            } else {
                // Handle registration failure (e.g., show an error message)
                console.error('Registration failed');
            }
        } catch (error) {
            setError('Registration failed');
            console.error('Error:', error);
        }
    };

    return (
        <div className="user-registration-form">
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="username">Name:</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    />
                </div>
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
        </div>
    );
}

export default UserRegistrationForm;
