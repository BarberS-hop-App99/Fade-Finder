import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";

function Liked() {
    const navigate = useNavigate();

    const [likedPosts, setLikedPosts] = useState(
        JSON.parse(localStorage.getItem('likedPosts')) || []
    );

    return (
        <div style={styles.container}>
            <nav style={styles.navbar}>
                <button style={styles.navButton} onClick={() => navigate('/customer/screen')}>
                    Home
                </button>
                <button style={styles.navButton} onClick={() => navigate('/customer/profile')}>
                    Customer Profile
                </button>
                <button style={styles.navButton} onClick={() => navigate('/liked')}>
                    Liked
                </button>
                <button style={styles.navButton} onClick={() => navigate('/search')}>
                    Search
                </button>
            </nav>
            <h1 style={styles.title}>Liked Posts</h1>
            {likedPosts.length === 0 ? (
                <p style={styles.noPosts}>You have not liked any posts yet.</p>
            ) : (
                <div style={styles.postsContainer}>
                    {likedPosts.map((post) => (
                        <div key={post.id} style={styles.post}>
                            <img
                                src={post.imageUrl}
                                alt={post.description}
                                style={styles.image}
                            />
                            <p style={styles.description}>{post.description}</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}
const styles = {
    container: {
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #4b3621, #2c2c2c)', // Smooth gradient background
        color: '#fff',
        fontFamily: "'Poppins', sans-serif",
        padding: '2rem',
    },
    title: {
        fontSize: '2.5rem',
        marginTop: '5rem', // Added margin to separate from the navbar
        marginBottom: '1.5rem',
        fontWeight: 'bold',
        textAlign: 'center',
        textShadow: '2px 2px 4px rgba(0, 0, 0, 0.7)',
    },
    noPosts: {
        fontSize: '1.2rem',
        color: '#fff',
        textAlign: 'center',
        marginTop: '2rem',
    },
    postsContainer: {
        display: 'flex',
        flexWrap: 'wrap',
        gap: '1.5rem',
        justifyContent: 'center',
    },
    post: {
        width: '300px',
        borderRadius: '10px',
        overflow: 'hidden',
        backgroundColor: '#fff',
        color: '#333',
        boxShadow: '0 8px 16px rgba(0, 0, 0, 0.2)',
        textAlign: 'center',
        transition: 'transform 0.2s',
    },
    image: {
        width: '100%',
        height: '200px',
        objectFit: 'cover',
    },
    description: {
        padding: '1rem',
        fontSize: '1rem',
        fontWeight: '500',
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
        transition: 'color 0.3s ease',
    },
};

export default Liked;
