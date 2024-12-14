import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function SignUp() {
    const [role, setRole] = useState(null); // Tracks selected role: Barber or Customer
    const [formData, setFormData] = useState({}); // Stores form input data
    const [message, setMessage] = useState('');
    const [signUpSuccess, setSignUpSuccess] = useState(false); // Tracks sign-up success
    const navigate = useNavigate();

    const handleRoleSelection = (selectedRole) => {
        setRole(selectedRole);
        setMessage(''); // Clear messages when selecting a role
        setSignUpSuccess(false); // Reset success state
    };

    const handleInputChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {


            // Check if the username exists
            const checkUsernameResponse = await fetch(
                `http://localhost:8080/${role.toLowerCase()}s/check-username?username=${formData.username}`
            );

            if (checkUsernameResponse.status === 409) {
                setMessage('Error: Username already exists. Please choose a different one.');
                return;
            }

            const endpoint =
                role === 'Barber' ? 'http://localhost:8080/barbers/create' : 'http://localhost:8080/customers/create';

            // Proceed with account creation
            const response = await fetch(endpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            const result = await response.json();

            if (response.ok) {
                setMessage(`Sign Up successful!`);
                setSignUpSuccess(true); // Enable the "Return to Sign In" button
            } else {
                setMessage(`Error: ${result.message}`);
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <div style={styles.container}>
            {!role ? (
                <>
                    <h1 style={styles.title}>Sign Up</h1>
                    <p style={styles.subtitle}>Choose your role:</p>
                    <div style={styles.buttonContainer}>
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
                    <h1 style={styles.title}>{role} Sign Up</h1>

                    <div style={styles.inputGroup}>
                        <label htmlFor="username" style={styles.label}>
                            Username:
                        </label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            style={styles.input}
                            onChange={handleInputChange}
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
                            style={styles.input}
                            onChange={handleInputChange}
                            required
                        />
                    </div>

                    {role === 'Customer' ? (
                        <div style={styles.inputGroup}>
                            <label htmlFor="postalCode" style={styles.label}>
                                Postal Code:
                            </label>
                            <input
                                type="text"
                                id="postalCode"
                                name="postalCode"
                                style={styles.input}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                    ) : (
                        <>
                            <div style={styles.inputGroup}>
                                <label htmlFor="barberPostalCode" style={styles.label}>
                                    Barber Postal Code:
                                </label>
                                <input
                                    type="text"
                                    id="barberPostalCode"
                                    name="barberPostalCode"
                                    style={styles.input}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>

                            <div style={styles.inputGroup}>
                                <label htmlFor="instagramUrl" style={styles.label}>
                                    Instagram URL:
                                </label>
                                <input
                                    type="text"
                                    id="instagramUrl"
                                    name="instagramUrl"
                                    style={styles.input}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                        </>
                    )}

                    {message && <p style={styles.message}>{message}</p>}

                    {signUpSuccess && (
                        <button
                            style={styles.signInButton}
                            onClick={() => navigate('/')}
                        >
                            Return to Sign In
                        </button>
                    )}

                    {!signUpSuccess && (
                        <button type="submit" style={styles.submitButton}>
                            Submit
                        </button>
                    )}
                </form>
            )}
        </div>
    );
}

const styles = {
    // Same styles as before
};

export default SignUp;
