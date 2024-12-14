import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { GoogleMap, LoadScript } from '@react-google-maps/api';


const containerStyle = {
    width: '100%',
    height: '400px', // Adjust height as needed
};

const center = {
    lat: 37.7749,
    lng: -122.4194,
};

const Search = () => {
    const [formData, setFormData] = useState({ BarberPostalCode: ''}); // Tracks input fields
    const [barberCount, setBarberCount] = useState(null);
    const [error, setError] = useState(''); // Tracks error messages


    const handleInputChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSearch = async (e) => {
        e.preventDefault();
        setError('');

        if (formData.BarberPostalCode) {
            try {
                    const response = await fetch(`http://localhost:8080/barbers/count?BarberPostalCode=${formData.BarberPostalCode}`, {
                    method: 'GET',
                });

                if (response.ok)
                {
                    const data = await response.json();

                    setBarberCount(data.count);

                }

                else{
                    console.error('Failed to fetch barber count:', response.status, response.statusText);
                    alert('Failed to fetch barber count. Please try again.');
                }
            } catch (e) {
                console.error('Error during search:', error.message);
                alert('An error occurred while searching. Please check the console for details.');
            }
        }
    };


    return (
        <div style={styles.container}>
            <h1 style={styles.title}>Search Barbers by Postal Code</h1>
            <div style={styles.inputContainer}>
                <input
                    type="text"
                    id="BarberPostalCode"
                    name="BarberPostalCode"
                    value={formData.BarberPostalCode}
                    onChange={handleInputChange}
                    placeholder="Enter Postal Code"
                    style={styles.input}
                />
                <button onClick={handleSearch} style={styles.button}>
                    Search
                </button>
            </div>
            {barberCount !== null && (
                <div style={styles.result}>
                    <p>
                        {`There ${
                            barberCount === 1 ? 'is' : 'are'
                        } ${barberCount} barber${
                            barberCount === 1 ? '' : 's'
                        } in postal code ${formData.BarberPostalCode}.`}
                    </p>
                </div>
            )}
            <LoadScript googleMapsApiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY}>
                <GoogleMap
                    mapContainerStyle={containerStyle}
                    center={center}
                    zoom={10}
                >
                    {/* Add markers or other map elements here if needed */}
                </GoogleMap>
            </LoadScript>
        </div>
    );
};

const styles = {
    container: {
        padding: '2rem',
        fontFamily: "'Poppins', sans-serif",
        backgroundColor: '#f9f9f9',
        color: '#333',
    },
    title: {
        textAlign: 'center',
        fontSize: '2rem',
        marginBottom: '1rem',
        fontWeight: 'bold',
    },
    inputContainer: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        gap: '1rem',
        marginBottom: '1rem',
    },
    input: {
        padding: '0.8rem',
        fontSize: '1rem',
        border: '1px solid #ccc',
        borderRadius: '5px',
        width: '250px',
    },
    button: {
        padding: '0.8rem 1.5rem',
        fontSize: '1rem',
        color: '#fff',
        backgroundColor: '#007BFF',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        fontWeight: 'bold',
    },
    result: {
        textAlign: 'center',
        marginTop: '1rem',
        fontSize: '1.2rem',
        color: '#333',
    },
};

export default Search;
