import React, { useEffect, useState } from 'react';
import { Table, TableHead, TableRow, TableCell, TableBody, Chip } from '@mui/material';
import { getLocomotiveSummary, getStatusSummary } from '../apis/index.js';

function LocomotiveTable({ summaryData }) {
    const formatDate = (datetime) => {
        console.log(datetime)
        const year = datetime.slice(0, 4);
        const month = datetime.slice(5, 7);
        const day = datetime.slice(8, 10);
        const hour = datetime.slice(11, 13);
        const minute = datetime.slice(14, 16);
        const second = datetime.slice(17, 19);

        return `${month}/${day}/${year} ${hour}:${minute}:${second}`;
    };

    const tableHeaders = [
        { id: 'code', label: 'Code' },
        { id: 'name', label: 'Name' },
        { id: 'active', label: 'Active' },
        { id: 'inactive', label: 'Inactive' },
        { id: 'under_maintenance', label: 'Under Maintenance' },
        { id: 'updated_at', label: 'Updated At' },
    ];

    return (
        <Table sx={{ borderRadius: '12px', overflow: 'hidden' }}>
            <TableHead>
                <TableRow>
                    {tableHeaders.map((header) => (
                        <TableCell key={header.id} align="center">
                            {header.label}
                        </TableCell>
                    ))}
                </TableRow>
            </TableHead>
            <TableBody>
                {summaryData.map((row) => (
                    <TableRow key={row.code}>
                        {tableHeaders.map((header) => (
                            <TableCell
                                key={header.id}
                                align="center"
                                sx={{
                                    borderBottom: '1px solid rgba(224, 224, 224, 1)',
                                    borderRadius: '0',
                                    paddingY: '15px',
                                }}
                            >
                                {header.id === 'active' || header.id === 'inactive' || header.id === 'under_maintenance' ? (
                                    <Chip
                                        label={row[header.id]}
                                        color="primary"
                                        sx={{
                                            padding: '10px 8px',
                                            height: 0,
                                            backgroundColor:
                                                header.id === 'active'
                                                    ? '#36AE7C'
                                                    : header.id === 'inactive'
                                                        ? '#EB5353'
                                                        : header.id === 'under_maintenance'
                                                            ? '#F9D923'
                                                            : 'default',
                                            color: '#FFFFFF',
                                        }}
                                    />
                                ) : header.id === 'updated_at' ? (
                                    <span>
                                        {formatDate(row[header.id].toString())}
                                    </span>
                                ) : (
                                    row[header.id]
                                )}
                            </TableCell>
                        ))}
                    </TableRow>
                ))}
            </TableBody>
        </Table>
    );
}

export default LocomotiveTable;
