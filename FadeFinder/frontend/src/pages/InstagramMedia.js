import React, { useEffect, useState } from "react";

function InstagramMedia({ barberId }) {
    const [media, setMedia] = useState([]);

    useEffect(() => {
        const fetchMedia = async () => {
            const response = await fetch(`/barbers/${barberId}/instagram-media`);
            if (response.ok) {
                const data = await response.json();
                setMedia(data.data || []); // Instagram API returns `data` array
            }
        };

        fetchMedia();
    }, [barberId]);

    return (
        <div>
            <h3>Instagram Media</h3>
            <div style={{ display: "flex", flexWrap: "wrap" }}>
                {media.map((item) => (
                    <div key={item.id} style={{ margin: "10px" }}>
                        {item.media_type === "IMAGE" && (
                            <img
                                src={item.media_url}
                                alt={item.caption}
                                style={{ width: "200px", height: "200px" }}
                            />
                        )}
                        {item.media_type === "VIDEO" && (
                            <video width="200" height="200" controls>
                                <source src={item.media_url} type="video/mp4" />
                            </video>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default InstagramMedia;
