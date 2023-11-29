import { Snackbar, Backdrop } from "@mui/material";

export default function AlertSnackBar({ open, handleCloseModal, column }) {
    const vertical = "top";
    const horizontal = "center";
    let message = null;

    if (column === 'Internal Server' || column === 0) {
        message = 'Terjadi kesalahan server. Silakan coba kembali.';
    }

    return (
        <div>
            <Snackbar
                anchorOrigin={{ vertical, horizontal }}
                open={open}
                onClose={handleCloseModal}
                autoHideDuration={6000}
                message={message}
                key={vertical + horizontal}
                ContentProps={{
                    sx: {
                        backgroundColor: '#CF1D1D',
                        fontFamily: "Inter, sans-serif",
                        fontWeight: 600,
                        display: 'block',
                        textAlign: "center",
                        paddingX: 4,
                    }
                }}
            />
            <Backdrop sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }} open={open}/>
        </div>
    );
}
