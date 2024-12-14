import React, { useState, useEffect } from 'react';
import { GoogleMap, LoadScript, Marker } from '@react-google-maps/api';

const MapComponent = () => {
    const containerStyle = {
        width: '100%',
        height: '400px', // Adjust height as needed
    };

    const center = {
        lat: 37.7749,
        lng: -122.4194,
    };

    return (
        <LoadScript
            googleMapsApiKey={process.env.AIzaSyCti_u7XOCtkwEl3weLDKumTDmZ_70zI1M}
        >
            <GoogleMap
                mapContainerStyle={containerStyle}
                center={center}
                zoom={10}
            >
                {/* Add your markers, etc., here */}
            </GoogleMap>
        </LoadScript>
    );
};

export default MapComponent;