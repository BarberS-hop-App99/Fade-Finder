import React from 'react';
import { useNavigate } from 'react-router-dom';

function Home() {
    const navigate = useNavigate();

    const handleSignIn = () => {
        navigate('/signin'); // Navigate to SignIn page
    };

    const handleSignUp = () => {
        navigate('/signup'); // Navigate to SignUp page
    };

    return (
        <div style={styles.container}>
            <header style={styles.header}>
                <h1 style={styles.title}>FadeFinder</h1>
            </header>
            <div style={styles.content}>
                <img
                    src="https://i.etsystatic.com/22890641/r/il/84f282/4754333264/il_fullxfull.4754333264_d0k2.jpg"
                    alt="FadeFinder Logo"
                    style={styles.image}
                />
                <div style={styles.buttonContainer}>
                    <button
                        style={styles.button}
                        onMouseOver={(e) => (e.target.style.backgroundColor = '#3a2a1a')}
                        onMouseOut={(e) => (e.target.style.backgroundColor = '#f5f5f5')}
                        onClick={handleSignIn}
                    >
                        Sign In
                    </button>
                    <button
                        style={styles.button}
                        onMouseOver={(e) => (e.target.style.backgroundColor = '#3a2a1a')}
                        onMouseOut={(e) => (e.target.style.backgroundColor = '#f5f5f5')}
                        onClick={handleSignUp}
                    >
                        Sign Up
                    </button>
                </div>
            </div>
        </div>
    );
}

const styles = {
    container: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        background: 'linear-gradient(135deg, #4b3621, #2c2c2c)', // Smooth gradient
        fontFamily: "'Poppins', sans-serif",
        color: '#fff',
    },
    header: {
        position: 'absolute',
        top: '20px',
        right: '20px',
        fontSize: '1.2rem',
        fontWeight: 'bold',
    },
    title: {
        fontSize: '3.5rem',
        fontWeight: 'bold',
        color: '#f5f5f5',
        textShadow: '2px 2px 4px rgba(0, 0, 0, 0.7)',
    },
    content: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        textAlign: 'center',
        marginTop: '-50px',
    },
    image: {
        width: '500px', // Increased width
        height: 'auto',
        borderRadius: '15px',
        boxShadow: '0 12px 24px rgba(0, 0, 0, 0.4)', // Deeper shadow for emphasis
        marginBottom: '3.5rem',
    },
    buttonContainer: {
        display: 'flex',
        flexDirection: 'column',
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
};


export default Home;
