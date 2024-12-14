import React from 'react';
import { useNavigate } from 'react-router-dom';

function Navbar() {
    const navigate = useNavigate();
    const userRole = localStorage.getItem('userRole'); // Get user role

    return (
        <nav style={styles.navbar}>
            <button style={styles.navButton} onClick={() => navigate('/')}>
                Home
            </button>
            {userRole === 'Barber' && (
                <button style={styles.navButton} onClick={() => navigate('/profile')}>
                    My Profile
                </button>
            )}
            {userRole === 'Customer' && (
                <button style={styles.navButton} onClick={() => navigate('/customer/profile')}>
                    Customer Profile
                </button>
            )}
        </nav>
    );
}

const styles = {
    navbar: {
        display: 'flex',
        justifyContent: 'space-around',
        backgroundColor: '#007BFF',
        padding: '1rem',
    },
    navButton: {
        color: '#fff',
        background: 'none',
        border: 'none',
        cursor: 'pointer',
    },
};

export default Navbar;
