import React from 'react';
import { AppBar, Toolbar, Typography, IconButton } from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import InfoIcon from '@mui/icons-material/Info';
import { Link } from 'react-router-dom';


const NavBar = () => {
    const iconButtonStyle = {
        color: '#000000',
    };

    return (
        <AppBar position="static" sx={{ backgroundColor: '#FFFFFF', color: '#000000', boxShadow: 'none' }}>
            <Toolbar>
                <Link to="/dashboard" color="inherit" style={{ textDecoration: 'none' }}>
                    <IconButton color="inherit" disableRipple sx={iconButtonStyle}>
                        <DashboardIcon />
                        <Typography variant="h6" sx={{ fontSize: '1rem', marginLeft: '3px' }}>
                            Dashboard
                        </Typography>
                    </IconButton>
                </Link>
                <div style={{ flexGrow: 1 }} />
                <Link to="/info" color="inherit" style={{ textDecoration: 'none' }}>
                    <IconButton color="inherit" disableRipple sx={iconButtonStyle}>
                        <InfoIcon />
                        <Typography variant="caption" sx={{ fontSize: '1rem', marginLeft: '3px' }}>Info</Typography>
                    </IconButton>
                </Link>
            </Toolbar>
        </AppBar>
    );
};

export default NavBar;
