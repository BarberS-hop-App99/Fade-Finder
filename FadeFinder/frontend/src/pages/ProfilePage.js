import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

function ProfilePage() {

    const navigate = useNavigate();
    const { username } = useParams();




    return (
        <div style={styles.container}>
            <nav style={styles.navbar}>
                <button style={styles.navButton} onClick={() => navigate('/')}>
                    Home
                </button>
                <button style={styles.navButton} onClick={() => navigate(`/profile/${username}`)}>
                    MyProfile
                </button>
                <button style={styles.navButton} onClick={() => navigate('/upload')}>
                    Post
                </button>
                <button style={styles.navButton} onClick={() => navigate('/search')}>
                    Search
                </button>
            </nav>
            <div style={styles.content}>
                <h1>Welcome to Your Profile!</h1>
            </div>
        </div>
    );
}



const styles = {
    container: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        height: '100vh',
        background: 'linear-gradient(135deg, #4b3621, #2c2c2c)', // Gradient similar to Home.js
        fontFamily: "'Poppins', sans-serif",
        color: '#fff',
    },
    navbar: {
        display: 'flex',
        justifyContent: 'space-around',
        alignItems: 'center',
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
    navButtonHover: {
        color: '#000000',
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
};


export default ProfilePage;
