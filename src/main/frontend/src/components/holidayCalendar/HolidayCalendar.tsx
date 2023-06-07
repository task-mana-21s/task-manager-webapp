import { useState, useEffect } from "react";
import axios from "axios";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import { Calendar } from "react-calendar";
import "./HolidayCalendar.css";
import "react-calendar/dist/Calendar.css";
import { format } from "date-fns";

interface Holiday {
  name: string;
  date: {
    iso: string;
  };
}

function HolidayCalendar() {
  const todayDate = format(new Date(), "yyyy-MM-dd");
  // mocked data to display Holiday
  // const todayDate = format(new Date(), "2023-12-24");
  const [holidayName, setHolidayName] = useState<string>("");
  const API_KEY = "2a998e4a7eb9363f1ec9d9c9b5deb730f82c7387";
  const country = "LT";
  const year = "2023";

  useEffect(() => {
    const fetchHolidays = async () => {
      try {
        const response = await axios.get(
          `https://calendarific.com/api/v2/holidays?api_key=${API_KEY}&country=${country}&year=${year}`
        );
        const holidays: Holiday[] = response.data.response.holidays;
        
        if (holidays.length > 0) {
          const todayHoliday = holidays.find(
            (holiday) => holiday.date.iso === todayDate
          );
          setHolidayName(todayHoliday ? todayHoliday.name : "");
        }
      } catch (error) {
        console.error("Error fetching holidays:", error);
      }
    };

    fetchHolidays();
  }, [todayDate, holidayName]);

  const [show, setShow] = useState(false);

  return (
    <div className="calendar-container">
      <div className="calendar-text">
        {holidayName && <p className="holiday-text">{holidayName} -</p>}
        <p className="date-text">{todayDate}</p>
      </div>
      <CalendarMonthIcon
        onClick={() => setShow(!show)}
        className="calendarIcon"
      />
      {show && (
        <div className="calendar">
          <Calendar />
        </div>
      )}
    </div>
  );
}

export default HolidayCalendar;
