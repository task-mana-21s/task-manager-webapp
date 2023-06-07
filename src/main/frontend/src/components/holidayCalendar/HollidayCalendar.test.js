import { render, screen, waitFor } from '@testing-library/react';
import HolidayCalendar from './HolidayCalendar';

describe('HolidayCalendar', () => {
  beforeEach(() => {
    console.error = jest.fn();
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  it('displays the holiday name and date', async () => {
    const mockResponse = {
      response: {
        holidays: [
          {
            name: 'Test Holiday',
            date: {
              iso: '2023-06-05',
            },
          },
        ],
      },
    };

    jest.mock('axios', () => ({
      get: jest.fn().mockResolvedValue({ data: mockResponse }),
    }));

    render(<HolidayCalendar />);

    // Wait for the component to update after the API request is complete
    await waitFor(() => {
      expect(screen.getByText('Test Holiday -')).toBeInTheDocument();
      expect(screen.getByText('2023-06-05')).toBeInTheDocument();
    });
  });

  it('handles API error', async () => {
    jest.spyOn(console, 'error');

    const errorMessage = 'API Error';
    jest.mock('axios', () => ({
      get: jest.fn().mockRejectedValue(new Error(errorMessage)),
    }));

    render(<HolidayCalendar />);

    // Wait for the component to update after the API request is complete
    await waitFor(() => {
      expect(screen.queryByText('Test Holiday -')).not.toBeInTheDocument();
      expect(screen.queryByText('2023-06-05')).not.toBeInTheDocument();
      expect(console.error).toHaveBeenCalledWith(
        'Error fetching holidays:',
        expect.any(Error)
      );
      expect(console.error).toHaveBeenCalledWith(
        errorMessage,
        expect.any(Error)
      );
    });
  });
});
