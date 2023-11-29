const express = require('express');
const kafka = require('kafka-node');
const mongoose = require('mongoose');

const app = express();
const port = 3001;

mongoose.connect('mongodb://127.0.0.1:27017/locomotive-db')
    .then(() => {
        console.log('Terhubung ke database MongoDB');
    })
    .catch((err) => {
        console.error('Gagal terhubung ke MongoDB:', err);
    });

const dataSchema = new mongoose.Schema({
    Code: String,
    Name: String,
    Dimension: String,
    Status: String,
    DateTime: String,
});

const Data = mongoose.model('locomotive_infos', dataSchema);

const client = new kafka.KafkaClient({ kafkaHost: 'localhost:9092' });
const consumer = new kafka.Consumer(client, [{ topic: 'loco' }]);

consumer.on('ready', () => {
    console.log('Terhubung ke Kafka');
});

consumer.on('error', (err) => {
    console.error('Ada kesalahan:', err);
});

const dataToSave = [];
consumer.on('message', async (message) => {
    const dataFromKafka = extractInfo(message.value);
    if (dataFromKafka) {
        dataToSave.push(dataFromKafka);
    }
});

setInterval(async () => {
    if (dataToSave.length > 0) {
        try {
            await Data.insertMany(dataToSave);
            console.log(dataToSave);
            console.log('Data disimpan ke MongoDB.');
            dataToSave.length = 0;
        } catch (error) {
            console.error('Gagal menyimpan data ke MongoDB:', error);
        }
    }
}, 10000);

app.listen(port, () => {
    console.log(`Server Express berjalan di http://localhost:${port}`);
});

const extractInfo = (message) => {
    const regex = /Code: (.*?), Name: (.*?), Dimension: (.*?), Status: (.*?), Date and Time: (.*)/;
    const match = message.match(regex);

    if (match && match.length === 6) {
        return {
            Code: match[1],
            Name: match[2],
            Dimension: match[3],
            Status: match[4],
            DateTime: match[5]
        };
    }
    return null;
};