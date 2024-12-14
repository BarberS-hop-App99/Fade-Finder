import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";


function CustomerScreen() {
    const navigate = useNavigate();

    // Hardcoded posts
    const posts = [
        {
            id: 1,
            imageUrl: 'https://www.bona.co.za/wp-content/uploads/2024/01/Snapinsta.app_349448536_1284896265786223_5778080184042987507_n_1080.jpg',
            description: 'Clean fade with sharp edges.',
        },
        {
            id: 2,
            imageUrl: 'https://i.pinimg.com/564x/10/6e/db/106edbf4090a1452f6a51ff8cd716f16.jpg',
            description: 'Waves Taper Cut.',
        },
        {
            id: 3,
            imageUrl: 'https://i.ytimg.com/vi/fdtFr2sbvgg/sddefault.jpg',
            description: 'High Top Fade.',
        },
    ];

    const handleLike = (postId) => {
        alert(`Liked post ${postId}`);
    };

    const handleDislike = (postId) => {
        alert(`Disliked post ${postId}`);
    };

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
            <h1 style={styles.title}>Haircut Posts</h1>
            <div style={styles.postsContainer}>
                {posts.map((post) => (
                    <div key={post.id} style={styles.post}>
                        <img
                            src={post.imageUrl}
                            alt={post.description}
                            style={styles.image}
                        />
                        <p style={styles.description}>{post.description}</p>
                        <div style={styles.actions}>
                            <button
                                onClick={() => handleLike(post.id)}
                                style={styles.likeButton}
                            >
                                Like
                            </button>
                            <button
                                onClick={() => handleDislike(post.id)}
                                style={styles.dislikeButton}
                            >
                                Dislike
                            </button>
                        </div>
                    </div>
                ))}
            </div>
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
        marginBottom: '1.5rem',
        fontWeight: 'bold',
        textAlign: 'center',
        textShadow: '2px 2px 4px rgba(0, 0, 0, 0.7)',
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
        color: '#00000',
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
    actions: {
        display: 'flex',
        justifyContent: 'space-between',
        padding: '0.5rem 1rem',
    },
    likeButton: {
        backgroundColor: '#28a745',
        color: '#fff',
        border: 'none',
        padding: '0.5rem 1rem',
        cursor: 'pointer',
        borderRadius: '5px',
        fontWeight: 'bold',
        transition: 'background-color 0.3s',
    },
    dislikeButton: {
        backgroundColor: '#dc3545',
        color: '#fff',
        border: 'none',
        padding: '0.5rem 1rem',
        cursor: 'pointer',
        borderRadius: '5px',
        fontWeight: 'bold',
        transition: 'background-color 0.3s',
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
    navButtonHover: {
        color: '#fff',
    },
};

export default CustomerScreen;
