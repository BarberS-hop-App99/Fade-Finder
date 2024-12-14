import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

function CustomerProfile() {
    const { password } = useParams();

    const { username } = useParams();


    const [customer, setCustomer] = useState({});
    const [isEditing, setIsEditing] = useState({
        newUsername: false,
        postalCode: false,
        newPassword: false,
    });
    const [showReSignIn, setShowReSignIn] = useState(false); // Toggle for re-sign in form
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const navigate = useNavigate();
    

    const handleEditClick = (field) => {
        setIsEditing((prev) => ({
            ...prev,
            [field]: true,
        }));
    };

    const handleInputChange = (e) => {
        setCustomer({
            ...customer,
            [e.target.name]: e.target.value,
        });
    };

    const handleSave = async (field) => {
        try {
            const response = await fetch(`http://localhost:8080/customers/${username}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    [field]: customer[field],
                }),
            });
            if (response.ok) {
                alert(`${field} updated successfully!`);
            } else {
                alert(`Failed to update ${field}.`);
            }
        } catch (error) {
            console.error(`Error updating ${field}:`, error.message);
        } finally {
            setIsEditing((prev) => ({
                ...prev,
                [field]: false,
            }));
        }
    };

    const handleDeleteAccount = async () => {
        setShowReSignIn(true); // Show re-sign in form
    };

    const handleReSignInChange = (e) => {
        setCredentials({
            ...credentials,
            [e.target.name]: e.target.value,
        });
    };

    const handleReSignInSubmit = async (e) => {
        e.preventDefault();

        try {
            // Verify user credentials
            const loginResponse = await fetch('http://localhost:8080/customers/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(credentials),
            });

            if (loginResponse.ok) {
                // Proceed with deletion if login is successful
                const deleteResponse = await fetch(`http://localhost:8080/customers/?username=${username}&password=${password}`, {
                    method: 'DELETE',
                });

                if (deleteResponse.ok) {
                    alert('Account deleted successfully.');
                    navigate('/'); // Redirect to home page
                } else {
                    alert('Failed to delete account.');
                }
            } else {
                alert('Verification failed: Incorrect username or password.');
            }
        } catch (error) {
            console.error('Error during re-sign in or deletion:', error.message);
        } finally {
            setShowReSignIn(false); // Hide re-sign in form
        }
    };

    return (
        <div style={styles.container}>
            <nav style={styles.navbar}>
                <button style={styles.navButton} onClick={() => navigate('/customer/screen')}>
                    Home
                </button>
                <button style={styles.navButton} onClick={() => navigate(`/customer/profile/${username}`)}>
                    Customer Profile
                </button>
                <button style={styles.navButton} onClick={() => navigate('/liked')}>
                    Liked
                </button>
                <button style={styles.navButton} onClick={() => navigate('/search')}>
                    Search
                </button>
            </nav>
            <h1 style={styles.title}>Customer Profile</h1>
            <div style={styles.profileContainer}>
                <div style={styles.field}>
                    <label style={styles.label}>Username:</label>
                    {isEditing.username ? (
                        <input
                            type="text"
                            name="username"
                            value={customer.username || ''}
                            onChange={handleInputChange}
                            style={styles.input}
                        />
                    ) : (
                        <span style={styles.value}>{customer.username}</span>
                    )}
                    <button
                        style={styles.button}
                        onClick={() =>
                            isEditing.username ? handleSave('username') : handleEditClick('username')
                        }
                    >
                        {isEditing.username ? 'Save' : 'Edit'}
                    </button>
                </div>
                <div style={styles.field}>
                    <label style={styles.label}>Postal Code:</label>
                    {isEditing.postalCode ? (
                        <input
                            type="text"
                            name="postalCode"
                            value={customer.postalCode || ''}
                            onChange={handleInputChange}
                            style={styles.input}
                        />
                    ) : (
                        <span style={styles.value}>{customer.postalCode}</span>
                    )}
                    <button
                        style={styles.button}
                        onClick={() =>
                            isEditing.postalCode ? handleSave('postalCode') : handleEditClick('postalCode')
                        }
                    >
                        {isEditing.postalCode ? 'Save' : 'Edit'}
                    </button>
                </div>
                <div style={styles.field}>
                    <label style={styles.label}>Password:</label>
                    {isEditing.password ? (
                        <input
                            type="password"
                            name="password"
                            value={customer.password || ''}
                            onChange={handleInputChange}
                            style={styles.input}
                        />
                    ) : (
                        <span style={styles.value}>••••••••</span>
                    )}
                    <button
                        style={styles.button}
                        onClick={() =>
                            isEditing.password ? handleSave('password') : handleEditClick('password')
                        }
                    >
                        {isEditing.password ? 'Save' : 'Edit'}
                    </button>
                </div>
                <button style={styles.deleteButton} onClick={handleDeleteAccount}>
                    Delete Account
                </button>
            </div>
            {showReSignIn && (
                <div style={styles.reSignInContainer}>
                    <h2>Re-Sign In to Confirm Deletion</h2>
                    <form onSubmit={handleReSignInSubmit} style={styles.reSignInForm}>
                        <div style={styles.inputGroup}>
                            <label>Username:</label>
                            <input
                                type="text"
                                name="username"
                                value={credentials.username}
                                onChange={handleReSignInChange}
                                style={styles.input}
                                required
                            />
                        </div>
                        <div style={styles.inputGroup}>
                            <label>Password:</label>
                            <input
                                type="password"
                                name="password"
                                value={credentials.password}
                                onChange={handleReSignInChange}
                                style={styles.input}
                                required
                            />
                        </div>
                        <button type="submit" onClick={() => navigate('/')}>
                            Confirm Deletion
                        </button>
                    </form>
                </div>
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
        background: 'linear-gradient(135deg, #4b3621, #2c2c2c)', // Smooth gradient
        fontFamily: "'Poppins', sans-serif",
        color: '#fff',
    },
    navbar: {
        display: 'flex',
        justifyContent: 'space-around',
        width: '100%',
        backgroundColor: '#3a2a1a',
        padding: '1rem',
        position: 'fixed',
        top: 0,
        zIndex: 1000,
    },
    navButton: {
        color: '#c0c0c0',
        backgroundColor: 'transparent',
        border: 'none',
        fontSize: '1.2rem',
        fontWeight: 'bold',
        cursor: 'pointer',
        textTransform: 'uppercase',
        transition: 'color 0.3s ease',
    },
    content: {
        marginTop: '5rem', // To account for the fixed navbar
        textAlign: 'center',
    },
    title: {
        fontSize: '2.5rem',
        fontWeight: 'bold',
        color: '#f5f5f5',
        textShadow: '2px 2px 4px rgba(0, 0, 0, 0.7)',
    },
    profileContainer: {
        display: 'flex',
        flexDirection: 'column',
        gap: '1.5rem',
        width: '80%',
        maxWidth: '400px',
        margin: '0 auto',
    },
    field: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    label: {
        fontWeight: 'bold',
    },
    value: {
        flex: 1,
        margin: '0 1rem',
    },
    input: {
        flex: 1,
        margin: '0 1rem',
        padding: '0.8rem',
        borderRadius: '5px',
        border: '1px solid #ccc',
    },
    button: {
        padding: '0.5rem 1rem',
        backgroundColor: '#007BFF',
        color: '#fff',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
    },
    deleteButton: {
        marginTop: '1rem',
        padding: '0.8rem 1.5rem',
        backgroundColor: '#dc3545',
        color: '#fff',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        fontWeight: 'bold',
    },
    reSignInContainer: {
        marginTop: '2rem',
        padding: '2rem',
        borderRadius: '10px',
        backgroundColor: '#3a2a1a',
        color: '#f5f5f5',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.3)',
    },
    reSignInTitle: {
        fontSize: '1.8rem',
        marginBottom: '1.5rem',
        textAlign: 'center',
        color: '#f5f5f5',
    },
    reSignInForm: {
        display: 'flex',
        flexDirection: 'column',
        gap: '1rem',
    },
    inputGroup: {
        display: 'flex',
        flexDirection: 'column',
        gap: '0.5rem',
    },
    confirmButton: {
        padding: '0.8rem 1.5rem',
        backgroundColor: '#dc3545',
        color: '#fff',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        textTransform: 'uppercase',
        fontWeight: 'bold',
        transition: 'background-color 0.3s ease',
    },

};

export default CustomerProfile;
