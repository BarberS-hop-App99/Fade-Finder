import React from 'react';

function PostDisplay({ post, onLike, onDismiss }) {
    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <button onClick={onLike} style={styles.likeButton}>
                    +
                </button>
            </div>
            <img src={post.imageUrl} alt={post.title} style={styles.image} />
            <div style={styles.footer}>
                <p style={styles.title}>{post.title}</p>
                <button onClick={onDismiss} style={styles.dismissButton}>
                    x
                </button>
            </div>
        </div>
    );
}

const styles = {
    container: {
        width: '300px',
        margin: '1rem',
        border: '1px solid #ccc',
        borderRadius: '8px',
        overflow: 'hidden',
        position: 'relative',
        backgroundColor: '#fff',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    },
    header: {
        display: 'flex',
        justifyContent: 'flex-end',
        padding: '0.5rem',
    },
    likeButton: {
        backgroundColor: '#007BFF',
        color: '#fff',
        border: 'none',
        borderRadius: '50%',
        width: '32px',
        height: '32px',
        fontSize: '1.2rem',
        cursor: 'pointer',
        textAlign: 'center',
        lineHeight: '32px',
    },
    image: {
        width: '100%',
        height: '200px',
        objectFit: 'cover',
    },
    footer: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '0.5rem',
    },
    title: {
        fontSize: '1rem',
        fontWeight: 'bold',
        color: '#333',
    },
    dismissButton: {
        backgroundColor: '#ff4d4d',
        color: '#fff',
        border: 'none',
        borderRadius: '50%',
        width: '32px',
        height: '32px',
        fontSize: '1.2rem',
        cursor: 'pointer',
        textAlign: 'center',
        lineHeight: '32px',
    },
};

export default PostDisplay;
