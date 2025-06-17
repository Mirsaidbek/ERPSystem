the main HR features currently implemented:

1. Employee Management:
   
   - User creation and management through `createEmployee`
   - Support for different employment models ( `EmploymentModel.java` defines FULL_TIME and PART_TIME )
   - Employee data management including personal information, hire dates, probation periods, and salary
2. Attendance Tracking:
   
   - Entry/Exit tracking through the `doEnter` and doExit endpoints
   - Time tracking functionality in `EmployeeController.java`
3. Leave Management:
   
   - Leave request creation and tracking
   - Status management (pending, approved)
   - Leave history tracking
   - Different types of leave requests supported
4. Document Management:
   
   - Resume upload, update, and deletion functionality
   - Document storage and retrieval system
5. User Information:
   
   - Comprehensive user profiles including:
     - Personal details (name, DateOfBirth, gender)
     - Contact information
     - Employment details
     - Marital status
     - Reporting structure
