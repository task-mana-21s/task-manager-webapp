import axios, { AxiosResponse } from 'axios';
import { useEffect, useState } from 'react';

export const getTemperatureForVilnius = (): Promise<number> => {
    return axios
        .get('http://localhost:8080/weather/vilnius')
        .then((response: AxiosResponse) => {
            const temperature: number = response.data.main.temp;
            return temperature;
        })
        .catch((error: any) => {
            console.error(error);
            throw error;
        });
};

const WeatherComponent = () => {
    const [temperature, setTemperature] = useState<number | null>(null);

    useEffect(() => {
        fetchTemperature();
    }, []);

    const fetchTemperature = async () => {
        try {
            const temp = await getTemperatureForVilnius();
            setTemperature(temp);
        } catch (error) {
            console.error(error);

        }
    };

    return (
        <div>
            <p>{temperature}°C</p>
        </div>
    );
};

export default WeatherComponent;
