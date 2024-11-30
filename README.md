İşte tek bir README.md dosyasına dönüştürülmüş hali:

# BloodGPT

BloodGPT is a comprehensive application designed to analyze blood test PDFs and provide medical insights and recommendations using AI. This project integrates **Spring Boot** for backend development and **ChatGPT API** for processing and interpreting medical data. The application aims to make healthcare data more accessible and insightful for patients and healthcare providers.

---

## Features

### **Core Features**
- **PDF Upload and Parsing**: Users can upload blood test PDFs downloaded from the MHRS system, and the system extracts relevant data.
- **ChatGPT Integration**: Blood test data is sent as a prompt to ChatGPT for detailed analysis.
- **Medical Insights**: The AI provides suggestions and comments based on blood test results, including flagging abnormal values.

### **Role-Based Access**
- **Patients**: 
  - Manage profiles (age, height, weight, BMI, email, phone number).
  - Upload blood test results and receive analyses.
- **Doctors**:
  - Linked to hospitals with specialty fields.
  - View and manage patient data.
  - Upload and analyze blood test results.

### **BloodAI Model**
- **Custom AI Development**: BloodAI is a specialized model trained on synthetic and real blood test datasets to provide domain-specific medical insights.
- **Balanced Dataset**: A synthetic dataset, located in the `data` package, ensures diverse scenarios for AI training, including balanced cases of "High," "Low," and "Normal" flags.

---

## Technologies Used

### **Backend**
- **Java 17**
- **Spring Boot 3.3.x**
- **ChatGPT API Integration** (`ChatModel`)
- **PDF Parsing**: Apache PDFBox
- **Database**: PostgreSQL for user, doctor, and blood test data
- **Security**: Spring Security with JWT for secure authentication
- **Swagger UI**: For API documentation
- **RESTful API Design**: Adheres to resource-based REST principles and SOLID principles

### **Frontend**
- **React.js**
- **Axios**: For API communication
- **Material-UI**: For responsive UI components

---

## Getting Started

### Prerequisites
- **Java 17** installed
- **PostgreSQL** installed and running
- **Node.js** installed (for frontend development)

### Installation

#### **Clone the Repository**

git clone https://github.com/BGNC/BloodGPT.git
cd BloodGPT

Backend Setup

	1.	Navigate to the backend directory:

cd backend


	2.	Configure application.properties with your PostgreSQL details:

spring.datasource.url=jdbc:postgresql://localhost:5432/bloodgpt
spring.datasource.username=your_username
spring.datasource.password=your_password

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs


	3.	Run the application:

./mvnw spring-boot:run



Frontend Setup

	1.	Navigate to the frontend directory:

cd frontend


	2.	Install dependencies:

npm install


	3.	Start the frontend application:

npm start

Dataset Information

The application includes a synthetic blood test dataset for AI training, located in the data package:
	•	Fields:
	•	Patient Name: The name of the patient.
	•	Test Name: Each blood test parameter.
	•	Test Result: The result value for the test.
	•	Reference Range: Normal range for the test.
	•	Status: Indicates whether the test result is “High,” “Low,” or “Normal.”
	•	Balanced Data: Ensures an even distribution of abnormal cases for AI training.

Example Data:

Patient Name	Test Name	Test Result	Reference Range	Status
John Doe	Hemoglobin	15.2 g/dL	13.5 - 17.5 g/dL	Normal
Jane Smith	WBC Count	4.0 x10^9/L	4.5 - 11.0 x10^9/L	Low

API Documentation

	•	Swagger UI is available at:

http://localhost:8091/swagger-ui.html


	•	Use this interactive documentation to test the available APIs.

Future Improvements

	•	Advanced AI Models: Integration with specialized healthcare models like BioBERT for domain-specific insights.
	•	Multi-language Support: To enable analyses and insights in different languages.
	•	OCR Integration: For handwritten notes in blood test PDFs.

Contribution

We welcome contributions! Feel free to fork this repository and submit pull requests.

License

This project is licensed under the MIT License. See the LICENSE file for details.

Contact

Developed by Buğra Onur Genç.
	•	Email: bugra34055@hotmail.com
	•	LinkedIn: Bugra Onur Genç
