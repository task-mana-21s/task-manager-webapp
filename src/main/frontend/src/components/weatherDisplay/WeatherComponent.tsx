import axios from 'axios';
import { useEffect, useState } from 'react';

/**

 A component that displays the current weather temperature in Vilnius.
 */

const WeatherComponent = () => {
  const [temperature, setTemperature] = useState<string | null>(null);

  useEffect(() => {

    /**

     Fetches the weather data from the API and updates the temperature state.
     */
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/weather/vilnius');
        const kelvinTemp = response.data.main.temp;
        const celsiusTemp = kelvinToCelsius(kelvinTemp);
        setTemperature(`${celsiusTemp}°C`);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  /**

   Converts temperature from Kelvin to Celsius.
   @param {number} kelvin - The temperature in Kelvin.
   @returns {number} The temperature in Celsius.
   */
  const kelvinToCelsius = (kelvin: number): number => {
    return Math.floor(kelvin - 273.15);
  };

  return (
    <div>{temperature}</div>
  );
};

export default WeatherComponent;
