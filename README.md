# Building a Locomotive System (Simulation) with React.js, Node.js, Java Spring, Kafka, MongoDB, MySQL, Logging, and Telegram Notifications

I made a locomotive simulation system with React, Node.js, Java Spring, Kafka, MongoDB, Oracle MySQL, logging, and Telegram Notifications. This system's strength lies in its use of microservices, breaking down tasks into smaller, cohesive units that function together seamlessly.

I used a Java Spring Scheduler to create a schedule that generates dummy locomotive data every 10 seconds. This data includes important info like the locomotive's code, name, dimension, status, and when it was recorded.
In handling the data, the Java Spring Scheduler takes charge of storing it. Simultaneously, the Node.js API helps out by fetching data from Kafka and smoothly moving it into MongoDB. This way of working ensures that the system manages locomotive data effectively, making it easy to handle a lot of data without slowing down.
Besides, I created a Scheduler Report using Java Spring Scheduler to produce detailed summaries every 11 seconds. This specialized system collects data from MongoDB and compiles essential locomotive information. These summaries are saved in MySQL databases, making it simple to retrieve and analyze them.
Furthermore, I added a notification feature to send these brief reports directly to Telegram for quick access. To keep track of everything and help with troubleshooting, I included a comprehensive logging system in the Scheduler. This whole setup ensures not just efficient report creation but also smooth delivery, while maintaining detailed logs for analysis and problem-solving.
For the user interface, I developed a Front-End Dashboard using React.js and Vite. This dashboard provides an intuitive and informative interface displaying real-time locomotive information for enhanced monitoring and analysis purposes.

Article:
https://www.linkedin.com/pulse/building-locomotive-system-simulation-reactjs-nodejs-satria-xl6tc
