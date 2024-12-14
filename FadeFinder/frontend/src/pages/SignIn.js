import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function SignIn() {
    const [role, setRole] = useState(null); // Tracks selected role: Barber or Customer
    const [formData, setFormData] = useState({ username: '', password: '' }); // Tracks input fields
    const [error, setError] = useState(''); // Tracks error messages
    const navigate = useNavigate();

    const handleRoleSelection = (selectedRole) => {
        setRole(selectedRole);
        setError(''); // Clear error messages when selecting a role
    };

    const handleInputChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(''); // Clear previous error messages

        if (formData.username && formData.password) {
            try {
                const endpoint = role === 'Barber' ? 'http://localhost:8080/barbers/login' : 'http://localhost:8080/customers/login';

                const response = await fetch(endpoint, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(formData),
                });

                if (response.ok) {
                    const userData = await response.json();

                    // Store role and user data in localStorage
                    localStorage.setItem('userRole', role);
                    localStorage.setItem('userData', JSON.stringify(userData));

                    // Navigate to the appropriate profile page
                    if (role === 'Barber') {
                        navigate(`/profile/${formData.username}`);
                    } else if (role === 'Customer') {
                        navigate(`/customer/profile/${formData.username}`);
                    }
                } else if (response.status === 404) {
                    setError('Incorrect Username or Password');
                } else {
                    const errorMessage = await response.text();
                    setError(`Sign in failed: ${errorMessage}`);
                }
            } catch (err) {
                console.error('Error during sign-in:', err);
                setError('An error occurred during sign-in. Please try again later.');
            }
        } else {
            setError('Please enter both username and password.');
        }
    };

    return (
        <div style={styles.container}>
            {!role ? (
                <>
                    <h1 style={styles.title}>Sign In</h1>
                    <p style={styles.subtitle}>Are you a Barber or a Customer?</p>
                    <div style={styles.roleButtons}>
                        <button style={styles.button} onClick={() => handleRoleSelection('Barber')}>
                            Barber
                        </button>
                        <button style={styles.button} onClick={() => handleRoleSelection('Customer')}>
                            Customer
                        </button>
                    </div>
                </>
            ) : (
                <form style={styles.form} onSubmit={handleSubmit}>
                    <h1 style={styles.title}>Sign In as {role}</h1>
                    {error && <p style={styles.error}>{error}</p>}

                    <div style={styles.inputGroup}>
                        <label htmlFor="username" style={styles.label}>
                            Username:
                        </label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            value={formData.username}
                            onChange={handleInputChange}
                            style={styles.input}
                            required
                        />
                    </div>

                    <div style={styles.inputGroup}>
                        <label htmlFor="password" style={styles.label}>
                            Password:
                        </label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={formData.password}
                            onChange={handleInputChange}
                            style={styles.input}
                            required
                        />
                    </div>

                    <button type="submit" style={styles.submitButton}>
                        Submit
                    </button>

                    <button style={styles.backButton} onClick={() => setRole(null)}>
                        Back to Role Selection
                    </button>
                </form>
            )}
        </div>
    );
}

const styles = {
    container: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #4b3621, #2c2c2c)', // Smooth gradient like Home.js
        fontFamily: "'Poppins', sans-serif",
        color: '#fff',
    },
    title: {
        fontSize: '3.5rem',
        fontWeight: 'bold',
        color: '#f5f5f5',
        textShadow: '2px 2px 4px rgba(0, 0, 0, 0.7)',
    },
    subtitle: {
        fontSize: '1.5rem',
        marginBottom: '2rem',
    },
    roleButtons: {
        display: 'flex',
        flexDirection: 'row',
        gap: '1.5rem',
    },
    button: {
        padding: '0.8rem 2rem',
        fontSize: '1.2rem',
        fontWeight: '600',
        color: '#4b3621',
        backgroundColor: '#f5f5f5',
        border: '2px solid #3a2a1a',
        borderRadius: '8px',
        cursor: 'pointer',
        transition: 'all 0.3s ease-in-out',
        textTransform: 'uppercase',
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        width: '100%',
        maxWidth: '400px',
        textAlign: 'center',
    },
    inputGroup: {
        marginBottom: '1.5rem',
    },
    label: {
        display: 'block',
        marginBottom: '0.5rem',
        fontWeight: 'bold',
    },
    input: {
        width: '100%',
        padding: '0.8rem',
        fontSize: '1rem',
        border: '1px solid #ccc',
        borderRadius: '4px',
    },
    submitButton: {
        padding: '0.8rem 2rem',
        fontSize: '1.2rem',
        fontWeight: '600',
        color: '#4b3621',
        backgroundColor: '#f5f5f5',
        border: '2px solid #3a2a1a',
        borderRadius: '8px',
        cursor: 'pointer',
        marginTop: '1rem',
        transition: 'all 0.3s ease-in-out',
    },
    backButton: {
        padding: '0.5rem 1.5rem',
        fontSize: '1rem',
        fontWeight: '500',
        color: '#4b3621',
        backgroundColor: '#f5f5f5',
        border: '1px solid #3a2a1a',
        borderRadius: '8px',
        cursor: 'pointer',
        marginTop: '1rem',
        transition: 'all 0.3s ease-in-out',
    },
    error: {
        color: '#dc3545', // Consistent with a professional error message style
        marginBottom: '1rem',
    },
};


export default SignIn;
