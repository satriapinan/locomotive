import React, { useState, useEffect } from 'react';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Typography, Box } from '@mui/material';
import LocomotiveTable from "../components/LocomotiveTable.jsx";
import NavBar from "../components/NavBar.jsx";
import StatusInfo from "../components/StatusInfo.jsx";
import {getLocomotiveSummary, getStatusSummary} from "../apis/index.js";
import AlertSnackBar from "../components/AlertSnackBar.jsx";

const theme = createTheme({
    typography: {
        fontFamily: [
            'Poppins',
            'sans-serif',
        ].join(','),
    },
    palette: {
        text: {
            primary: '#737373',
        },
    },
});

function Dashboard() {
    const [summaryStatusData, setSummaryStatusData] = useState([]);
    const [summaryLocomotiveData, setSummaryLocomotiveData] = useState([]);
    const [error, setError] = useState(false);

    const fetchStatusData = async () => {
        try {
            const response = await getStatusSummary();
            if (response.message == "Network Error") {
                setError(true);
            } else {
                setSummaryStatusData(response);
                setError(false);
            }
        } catch (error) {
            setError(true);
            console.error('Error fetching status data:', error);
        }
    };

    const fetchLocomotiveData = async () => {
        try {
            const response = await getLocomotiveSummary();
            console.log(response.message);

            if (response.message == "Network Error") {
                setError(true);
            } else {
                setSummaryLocomotiveData(response);
                setError(false);
            }

        } catch (error) {
            setError(true);
            console.error('Error fetching locomotive data:', error);
        }
    };

    useEffect(() => {
        fetchStatusData();
        fetchLocomotiveData();

        const statusInterval = setInterval(fetchStatusData, 10001);
        const locomotiveInterval = setInterval(fetchLocomotiveData, 11001);

        return () => {
            clearInterval(statusInterval);
            clearInterval(locomotiveInterval);
        };
    }, []);

    const activeCount = summaryStatusData.find(summary => summary.status === 'Active')?.total || 0;
    const inactiveCount = summaryStatusData.find(summary => summary.status === 'Inactive')?.total || 0;
    const maintenanceCount = summaryStatusData.find(summary => summary.status === 'Under Maintenance')?.total || 0;

    return (
        <ThemeProvider theme={theme}>
            <div>
                {error && (
                    <AlertSnackBar
                        open={true}
                        handleCloseModal={() => setError(false)}
                        column={'Internal Server'}
                    />
                )}
                <NavBar/>

                <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginY: 2, gap: 2, flexDirection: 'column'}}>
                    <StatusInfo
                        activeCount={activeCount}
                        inactiveCount={inactiveCount}
                        maintenanceCount={maintenanceCount}
                    />

                    <Box sx={{display: 'flex', width: '75%'}}>
                        <LocomotiveTable summaryData={summaryLocomotiveData} />
                    </Box>

                    <Typography fontSize="0.7rem" fontWeight={300} marginY={2}>© 2023 Locomotive Use Case</Typography>

                </Box>
            </div>
        </ThemeProvider>
    );
}

export default Dashboard;
