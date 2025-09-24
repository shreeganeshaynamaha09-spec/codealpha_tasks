ROYAL VILLAAS - A Full-Stack Hotel Reservation System
Welcome to the official repository for ROYAL VILLAAS, a complete, modern hotel reservation and management system. This full-stack application provides a seamless experience for guests to book their stay and a powerful, intuitive dashboard for administrators to manage the entire hotel's operations.

The project is built with a robust Spring Boot backend and a dynamic Angular frontend, demonstrating a real-world, professional web application architecture.

✨ Core Features
This application is divided into two main user experiences: the guest-facing website and the secure admin panel.

🏨 Guest Experience
Elegant Landing Page: A beautiful, fully-styled homepage for "ROYAL VILLAAS" that introduces the hotel and its services.

Dynamic Room Search: Guests can check the availability of room categories (Standard, Deluxe, Suite) for specific dates. The system intelligently returns a count of available rooms.

Secure User Authentication: Guests can create an account and log in securely. The system uses a modern authentication workflow.

Personalized User Dashboard: After logging in, guests are greeted with a personalized dashboard where they can:

View a complete history of all their past and upcoming bookings.

Track the real-time status of their reservations (Pending, Confirmed, Cancelled).

Request a new booking for an available room category.

🔑 Admin Panel
Secure Admin Login: A dedicated admin user (admin@hotel.com) is automatically created on startup for secure access.

Advanced Tabbed Dashboard: A sophisticated and clean UI for managing all hotel operations.

Complete Room Management (CRUD):

Create: Admins can add new rooms to the hotel's inventory, specifying the room number, category, price, and initial status.

Read: View a complete, real-time list of all rooms in the hotel.

Update: Edit a room's price or change its status (e.g., from AVAILABLE to MAINTENANCE).

Delete: Remove rooms from the inventory.

Professional Booking Management Workflow:

View Pending Requests: All new booking requests from users appear in the admin's dashboard.

Assign Rooms: For each pending request, the admin can see a dropdown of all available rooms that match the user's requested category and dates.

Confirm/Reject Bookings: The admin can either confirm a booking by assigning a specific room (which updates the room's status to OCCUPIED) or reject the request.

View All Bookings: Admins have access to a complete history of all reservations in the system.

🛠️ Technology Stack
This project utilizes a modern and robust set of technologies to deliver a high-performance web application.

Area

Technology

Backend

Spring Boot, Spring Data JPA, Spring Security, Maven, Java 17

Frontend

Angular, TypeScript, HTML5, CSS3

Database

H2 (Persistent File-based Mode)

API

RESTful API, tested and documented with Swagger UI

🚀 Getting Started
Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
Ensure you have the following software installed on your system:

Java Development Kit (JDK): Version 17 or higher.

Maven: Version 3.8 or higher.

Node.js and npm: Node.js version 18.x or higher (which includes npm).

Angular CLI: The latest version. Install globally with npm install -g @angular/cli.

An IDE of your choice (e.g., Eclipse, IntelliJ IDEA, VS Code).

Installation & Setup
1. Clone the Repository

First, clone this repository to your local machine.

git clone <your-repository-url>
cd <repository-folder>

You will see two project folders inside: reservation-system (the backend) and hotel-frontend (the frontend).

2. Set Up and Run the Backend (Spring Boot)

Navigate to the backend project directory:

cd reservation-system

Using your IDE (like Eclipse), perform a clean build to ensure all dependencies are correctly downloaded and the project is compiled.

Right-click on the project -> Run As -> Maven build...

In the "Goals" box, type clean install and click Run.

Once the build is successful, run the application.

Find the ReservationSystemApplication.java file.

Right-click on it -> Run As -> Spring Boot App.

The backend server will start on http://localhost:8080. On the first run, it will automatically create a persistent database file and seed the admin user.

Admin Credentials:

Email: admin@hotel.com

Password: admin123

3. Set Up and Run the Frontend (Angular)

Open a new terminal window.

Navigate to the frontend project directory:

cd ../hotel-frontend 

Install all the necessary node modules:

npm install

Start the Angular development server:

ng serve

The frontend application will start and be accessible at http://localhost:4200/.

🔗 Accessing the Application
Once both the backend and frontend are running, you can access the different parts of the system:

Main Website: http://localhost:4200/

Backend API Docs (Swagger): http://localhost:8080/swagger-ui.html

H2 Database Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:file:~/hotel_db

User Name: sa

Password: (leave blank)
