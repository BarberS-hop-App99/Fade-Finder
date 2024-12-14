import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

function UploadWork() {
    const { username } = useParams();
    const navigate = useNavigate();


    const [image, setImage] = useState(null);
    const [message, setMessage] = useState('');
    const [description, setDescription] = useState('');

    const handleImageChange = (e) => {
        console.log(e.target.files[0]);
        const file = e.target.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        console.log(reader.result);
        setImage(reader.result);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('imageUrl', image);
        console.log(image);
        if (image === null) {
            setMessage('Please select an image to upload.');
            return;
        }


        try {
            const response = await fetch(`http://localhost:8080/barbers/update/${username}`, {
                method: 'GET',
                body: formData,
            });

            if (response.ok) {
                setMessage('Image uploaded successfully!');
            } else {
                setMessage('Image Not Here!');
            }
        } catch (error) {
            setMessage('Image Failed');
        }
    };

    return (
        <div style={styles.container}>
            <nav style={styles.navbar}>
                {/* eslint-disable-next-line no-undef */}
                <button style={styles.navButton} onClick={() => navigate('/')}>
                    Home
                </button>
                {/* eslint-disable-next-line no-undef */}
                <button style={styles.navButton} onClick={() => navigate('/profile')}>
                    MyProfile
                </button>
                {/* eslint-disable-next-line no-undef */}
                <button style={styles.navButton} onClick={() => navigate('/upload')}>
                    Post
                </button>
                {/* eslint-disable-next-line no-undef */}
                <button style={styles.navButton} onClick={() => navigate('/search')}>
                    Search
                </button>
            </nav>
            <h1 style={styles.title}>Upload Haircut</h1>
            <form style={styles.form} onSubmit={handleImageChange}>
                <input
                    type="file"
                    accept="image/*"
                    onChange={handleSubmit}
                    style={styles.fileInput}
                />
                <textarea
                    placeholder="Add a description (optional)"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    style={styles.textarea}
                />
                <button type="submit" style={styles.button}>
                    Upload
                </button>
                {message && <p style={styles.message}>{message}</p>}
            </form>
        </div>
    );
}

const styles = {
    container: {
        padding: '2rem',
        fontFamily: 'Arial, sans-serif',
        textAlign: 'center',
    },
    title: {
        fontSize: '2rem',
        marginBottom: '1rem',
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    fileInput: {
        marginBottom: '1rem',
    },
    textarea: {
        width: '100%',
        maxWidth: '400px',
        minHeight: '80px',
        marginBottom: '1rem',
    },
    button: {
        padding: '0.8rem 1.5rem',
        fontSize: '1rem',
        color: '#fff',
        backgroundColor: '#007BFF',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
    },
    message: {
        marginTop: '1rem',
        color: 'green',
    },
    navbar: {
        display: 'flex',
        justifyContent: 'space-around',
        width: '100%',
        backgroundColor: '#4b3621',
        padding: '1rem 0',
    },
    navButton: {
        color: '#c0c0c0',
        backgroundColor: 'transparent',
        border: 'none',
        fontSize: '1rem',
        cursor: 'pointer',
    },
};

export default UploadWork;