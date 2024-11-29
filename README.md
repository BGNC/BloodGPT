

# BloodGPT

BloodGPT is a powerful application designed to analyze blood test PDFs and provide medical insights and suggestions using ChatGPT. This project leverages **Spring Boot** for backend development and integrates ChatGPT's API to process and interpret medical data efficiently.

## Features

- **PDF Upload**: Users can upload blood test PDFs downloaded from the MHRS system.
- **Automated Parsing**: The system extracts relevant data from the uploaded PDFs.
- **ChatGPT Integration**: Blood test data is sent as a prompt to ChatGPT for analysis.
- **Medical Suggestions**: The AI provides detailed comments or recommendations based on the blood test results.
- **User-Friendly Interface**: A React-based frontend for seamless user interaction.

## Technologies Used

### Backend
- **Java 17**
- **Spring Boot 3.3.x**
- **ChatGPT API Integration** (`ChatModel`)
- **PDF Parsing**: Apache PDFBox
- **Database**: PostgreSQL for storing user and blood test data
- **RESTful API Design**: Follows resource-based REST principles and adheres to SOLID principles

### Frontend
- **React.js**
- **Axios**: For API communication
- **Material-UI**: For responsive UI components

## Getting Started

### Prerequisites
- **Java 17** installed
- **PostgreSQL** installed and running
- **Node.js** (for frontend development)

### Installation

#### Clone the Repository
```bash
git clone https://github.com/BGNC/BloodGPT.git
cd BloodGPT

Backend Setup

	1.	Navigate to the backend directory:

cd backend


	2.	Configure application.properties with your PostgreSQL details:

spring.datasource.url=jdbc:postgresql://localhost:5432/bloodgpt
spring.datasource.username=your_username
spring.datasource.password=your_password


	3.	Run the application:

./mvnw spring-boot:run



Future Improvements

	•	Multi-language support for ChatGPT responses
	•	Integration with additional AI models for advanced medical insights
	•	Enhanced PDF parsing with OCR for handwritten notes

Contribution

We welcome contributions! Feel free to fork this repository and submit pull requests.

License

This project is licensed under the MIT License. See the LICENSE file for details.

Contact

Developed by Buğra Onur Genç.
	•	Email: bugra34055@hotmail.com
	•	LinkedIn: Bugra Onur Genç

