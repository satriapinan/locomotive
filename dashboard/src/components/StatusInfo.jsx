import React from 'react';
import { Divider, Stack, Typography } from '@mui/material';
import { PieChart } from '@mui/x-charts/PieChart';
import { useSpring, animated } from 'react-spring';

const AnimatedCount = ({ value }) => {
    const props = useSpring({
        value,
        from: { value: 0 },
        config: { duration: 500 },
    });

    return <animated.span>{props.value.interpolate((val) => Math.floor(val))}</animated.span>;
};

const StatusInfo = ({ activeCount, inactiveCount, maintenanceCount }) => {
    const active = activeCount ?? 0;
    const inactive = inactiveCount ?? 0;
    const maintenance = maintenanceCount ?? 0;

    return (
        <Stack
            direction={{ xs: 'column', sm: 'row' }}
            spacing={{ xs: 1, sm: 2, md: 4 }}
            divider={<Divider orientation="vertical" sx={{ height: '80px' }} />}
            sx={{ display: 'flex', alignItems: 'center' }}
        >
            <Stack>
                <Typography sx={{ fontWeight: 600, color: '#737373' }}>Active</Typography>
                <Typography sx={{ fontSize: '2rem', fontWeight: 500, color: '#36AE7C' }}>
                    <AnimatedCount value={active} />
                </Typography>
            </Stack>

            <Stack>
                <Typography sx={{ fontWeight: 600, color: '#737373' }}>Inactive</Typography>
                <Typography sx={{ fontSize: '2rem', fontWeight: 500, color: '#EB5353' }}>
                    <AnimatedCount value={inactive} />
                </Typography>
            </Stack>

            <Stack>
                <Typography sx={{ fontWeight: 600, color: '#737373' }}>Under Maintenance</Typography>
                <Typography sx={{ fontSize: '2rem', fontWeight: 500, color: '#F9D923' }}>
                    <AnimatedCount value={maintenance} />
                </Typography>
            </Stack>

            <PieChart
                colors={['#36AE7C', '#EB5353', '#F9D923']}
                series={[
                    {
                        data: [
                            { id: 0, value: active, label: 'Active' },
                            { id: 1, value: inactive, label: 'Inactive' },
                            { id: 2, value: maintenance, label: 'Under Maintenance' },
                        ],
                        innerRadius: 15,
                        outerRadius: 70,
                        paddingAngle: 5,
                        cornerRadius: 5,
                        cx: 65,
                        highlightScope: { faded: 'global', highlighted: 'item' },
                        faded: { innerRadius: 30, additionalRadius: -10, color: 'gray' },
                    },
                ]}
                width={370}
                height={200}
            />
        </Stack>
    );
};

export default StatusInfo;
