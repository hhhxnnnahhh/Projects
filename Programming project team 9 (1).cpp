// manager password is 123
// recp01 password is UMX
// recp02 password is specified by user (his first time loging in)

#include <iostream>
#include <cmath>
#include <cstring>
#include <iomanip>
#include <string>
#include <algorithm>
#include <vector>
using namespace std;

int residentsize;
string boarding;
string room_type;

struct date {
    int day = 1;
    int month = 1;
    int year = 2025;
};

struct occupied {
    bool available;
    date initial = { 0,0,0 };
    date final = { 0,0,0 };
    string value;
};

struct manager {
    int ID = 001;
    string name = "Samy";
    string username = "Sarah";
    string password = "123";
};

struct receptionist {
    double salary;
    int ID;
    string name;
    string username;
    string password = "TBA";
};

struct worker {
    double salary;
    int ID;
    string contact;
    string name;
    string JobTitle;
};

struct room {
    double price;
    int ID;
    string type;
    occupied check;
};

struct resident {
    double cost;
    int ID;
    int contact;
    date Staytime; // duration
    date checkin;
    date checkout;
    string name;
    int BoardingOption;// 1 if fullboard - 2 if halfboard - 3 if break and break
    string RoomType;
    room ownroom;
};

void dateoutput(date d) {
    cout << "        ";
    cout << setfill('0') << setw(2) << d.day << "/" << setw(2) << d.month << "/" << setw(4) << d.year << setfill(' ');
}

date durationCalc(date i, date f) {
    date dura;
    if (f.day - i.day < 0) {
        dura.day = 30 + (f.day - i.day);
    }
    else if (f.day - i.day == 0) {
        dura.day = 1;
    }
    else {
        dura.day = (f.day - i.day) + 1;
    }
    if (f.month - i.month < 0) {
        dura.month = 12 + (f.month - i.month);
    }
    else {
        dura.month = f.month - i.month;
    }
    while (true) {
        if (f.year - i.year < 0) {
            cout << "this year is invalid" << endl;
            cout << "enter another year" << endl;
            cin >> f.year;
        }
        else {
            dura.year = f.year - i.year;
            break;
        }
    }
    return dura;
}

void dateinput(date& d) {
    cout << endl << "Enter the day:" << endl;
    cin >> d.day;
    while (d.day > 30)
    {
        cout << "Days can't be more than 30, renter";
        cin >> d.day;
    }
    cout << "Enter the month :" << endl;
    cin >> d.month;
    while (d.month > 12)
    {
        cout << "Months can't be more than 12, renter";
        cin >> d.month;
    }
    cout << "Enter the year  :" << endl;
    cin >> d.year;
    while (d.year < 2025)
    {
        cout << "Year ccan't be less than 2025, renter";
        cin >> d.year;
    }
}

int cost_calculatorSolo() { //main
    system("cls");
    date initial;
    date final;
    cout << "for the initial date";
    dateinput(initial);
    cout << "for the final date";
    dateinput(final);
    date dura = durationCalc(initial, final);
    int room_cost = 0;
    int boarding_cost = 0;
    int total_cost = 0;
    char roomChoice;
    cout << "What type of room do you want " << endl
        << "1. Single" << endl
        << "2. Double" << endl
        << "3. triple" << endl;
    cin >> roomChoice;
    while (true) {
        switch (roomChoice) {
        case'1':
            room_cost = 4000;
            break;
        case '2':
            room_cost = 4500;
            break;
        case '3':
            room_cost = 5000;
            break;
        default:
            cout << "enter valid input (1/ 2 /3) : ";
            cin >> roomChoice;
            continue;
        }
        break;
    }

    cout << "What is your choice for boarding " << endl
        << "1. Full" << endl
        << "2. Half" << endl
        << "3. Breakfast and break" << endl;
    char BChoice;
    cin >> BChoice;
    while (true) {
        switch (BChoice) {
        case'1':
            boarding_cost = 200;
            break;
        case '2':
            boarding_cost = 125;
            break;
        case '3':
            boarding_cost = 75;
            break;
        default:
            cout << "enter valid input (1/ 2 /3) : ";
            cin >> BChoice;
            continue;
        }
        break;
    }

    total_cost = (boarding_cost + room_cost) * (dura.day + (dura.month * 30) + (dura.year * 360));
    cout << "total cost = " << total_cost;
    char choice = 0;
    cout << endl << "want to do another calculation ? (y/n)";
    while (true) {
        cin >> choice;
        if (choice == 'y' || choice == 'Y') {
            return 6;
        }
        else if (choice == 'n' || choice == 'N') {
            return 3;
        }
        else {
            cout << endl << "valid input please";
        }
    }
}

int cost_calculator(int roomChoice, date dura) {
    int room_cost = 0;
    int boarding_cost = 0;
    int total_cost = 0;
    while (true) {
        if (roomChoice == 101 || roomChoice == 102 || roomChoice == 103) {
            room_cost = 4000;
            room_type = "Single";
            break;
        }
        else if (roomChoice == 201 || roomChoice == 202 || roomChoice == 203 || roomChoice == 204) {
            room_cost = 4500;
            room_type = "Double";
            break;
        }
        else if (roomChoice == 101 || roomChoice == 102 || roomChoice == 103) {
            room_cost = 5000;
            room_type = "Triple";
            break;
        }
    }

    cout << "What is your choice for boarding " << endl
        << "1. All Inclusive" << endl
        << "2. Half Board" << endl
        << "3. Bed & Breakfast" << endl;
    int BChoice;
    cin >> BChoice;
    while (true) {
        if (BChoice == 1) {
            boarding_cost = 200;
            boarding = "All Inclusive";
            break;
        }
        else if (BChoice == 2) {
            boarding_cost = 125;
            boarding = "Half Board";
            break;
        }
        else if (BChoice == 3) {
            boarding_cost = 75;
            boarding = "Bed & Breakfast";
            break;
        }
        else {
            cout << "enter valid input (1/ 2 /3) : ";
            cin >> BChoice;
            continue;
        }
    }

    total_cost = (boarding_cost + room_cost) * (dura.day + (dura.month * 30) + (dura.year * 360));
    return total_cost;

}

int IncomeTable(resident residents[], worker workers[], receptionist receptionists[], int residentsize, int workersize, int receptionistsize) { //main
    while (true) {
        date d;// defulat
        cout << "\033[40A" << "\r-------------------------------------------------------------------------------" << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r-------------------------------------------------------------------------------" << endl;
        double total = 0;
        char userchoice;
        cout << "\033[15A" <<
            "Which report do you want: " << endl <<
            "1.weekly" << endl <<
            "2.monthly" << endl <<
            "3.annual" << endl <<
            "4.return" << endl <<
            "Input : ";
        cin >> userchoice;
        cout << "\033[15A" << "\r-------------------------------------------------------------------------------" << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r                                                                               " << endl
            << "\r-------------------------------------------------------------------------------" << endl;



        double totalsalary = 0;
        string x;

        switch (userchoice)
        {

        case '1':
            cout << "\033[14A" << "\rroom       boarding option       check-in time       earnings" << endl;
            for (int i = 0; i < residentsize; i++) {
                if (residents[i].BoardingOption = 1) {
                    x = "full board";
                }
                else if (residents[i].BoardingOption = 2) {
                    x = "half board";
                }
                else {
                    x = "Breakfast and Break";
                }
                date var = durationCalc(d, residents[i].checkin);
                if (var.day < 7 && var.month == 0 && var.year == 0) {
                    cout << setw(4) << residents[i].ownroom.ID << setw(22) << x << setw(10);
                    dateoutput(residents[i].checkin);
                    cout << setw(15) << residents[i].cost << endl;
                    total += residents[i].cost;
                }
            }
            for (int i = 0; i < receptionistsize; i++) {
                totalsalary += receptionists[i].salary;
            }
            for (int i = 0; i < workersize; i++) {
                totalsalary += workers[i].salary;
            }
            cout << endl << endl << endl;
            cout << "\rtotal cost" << setw(51) << totalsalary << endl;
            cout << "\rtotal earnings" << setw(47) << total - totalsalary << endl;
            break;

        case '2':
            cout << "\033[14A" << "\rroom       boarding option       check-in time       earnings" << endl;
            for (int i = 0; i < residentsize; i++) {
                if (residents[i].BoardingOption = 1) {
                    x = "full board";
                }
                else if (residents[i].BoardingOption = 2) {
                    x = "half board";
                }
                else {
                    x = "Breakfast and Break";
                }
                date var = durationCalc(d, residents[i].checkin);
                if (var.month < 1 && var.year == 0) {
                    cout << setw(4) << residents[i].ownroom.ID << setw(22) << residents[i].BoardingOption << setw(10);
                    dateoutput(residents[i].checkin);
                    cout << setw(15) << residents[i].cost << endl;
                    total += residents[i].cost;
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int i = 0; i < receptionistsize; i++) {
                    totalsalary += receptionists[i].salary;
                }

                for (int i = 0; i < workersize; i++) {
                    totalsalary += workers[i].salary;
                }
            }
            cout << endl << endl << endl;
            cout << "\rtotal cost" << setw(51) << totalsalary << endl;
            cout << "\rtotal earnings" << setw(47) << total - totalsalary << endl;
            break;
        case '3':
            cout << "\033[14A" << "\rroom       boarding option       check-in time       earnings" << endl;
            for (int i = 0; i < residentsize; i++) {
                date var = durationCalc(d, residents[i].checkin);
                if (var.year < 2) {
                    cout << setw(4) << residents[i].ownroom.ID << setw(22) << residents[i].BoardingOption << setw(10);
                    dateoutput(residents[i].checkin);
                    cout << setw(15) << residents[i].cost << endl;
                    total += residents[i].cost;
                }

            }
            for (int i = 0; i < 12; i++) {
                for (int i = 0; i < receptionistsize; i++) {
                    totalsalary += receptionists[i].salary;
                }

                for (int i = 0; i < workersize; i++) {
                    totalsalary += workers[i].salary;
                }
            }
            cout << endl << endl << endl;
            cout << "\rtotal cost" << setw(51) << totalsalary << endl;
            cout << "\rtotal earnings" << setw(47) << total - totalsalary << endl;
            break;
        case '4':
            return 2;
        default:
            cin.ignore();
            cout << "\033[14A" << "Input a number from 1 to 4 please ";
            continue;
        }
        bool restart;
        char input;
        cout << endl << "do you wish to continue (y/n)";

        while (true) {
            cin >> input;
            switch (input) {
            case'y':
            case'Y':
                restart = 1;
                break;
            case 'n':
            case'N':
                restart = 0;
                break;
            default:
                cin.ignore(numeric_limits<streamsize>::max(), '\n');
                cout << "\033[1A" << "\r                                                               ";
                cout << "\rvalid input please (y/n)";
                continue;
            }
            break;
        }
        if (restart == 0) {
            return 2;
        }
    }
}

int managerlogin(string managerpassword)
{
    int count = 3;
    manager manager;
    manager.password = "123";
    while (managerpassword != manager.password)
    {
        cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl
            << "\r|                              Hello Manager                                    |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                          please insert your password                          |" << endl
            << "\r|         Invalid login, you have " << count << " more tries to retry           |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|Input:                                                                         |" << endl
            << "\r_________________________________________________________________________________" << endl;
        cout << "\033[1A" << "       ";
        cin >> managerpassword;
        count--;
        if (count <= 0)
        {
            return 1;
        }
    }
    return 2;
}

int receptionistusernamelogin(receptionist arrReceptionists[])
{
    int index;
    string receptionistusername;
    int valid = 0, count = 3;
    while (valid == 0)
    {

        cin >> receptionistusername;
        for (int i = 0; i < 7; i++)
        {
            if (arrReceptionists[i].username == receptionistusername)
            {
                valid = 1;
                index = i;
                return index;
            }
        }
        cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl
            << "\r|                              Hello Receptionists                              |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                          please insert your Username                          |" << endl
            << "\r|         Invalid login, you have " << count << " more tries to retry           |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|Input:                                                                         |" << endl
            << "\r_________________________________________________________________________________" << endl;
        cout << "\033[1A" << "       ";
        if (count <= 0)
        {
            return -1;
        }
        count--;

    }
}

int receptionistpasswordlogin(receptionist arrReceptionists[], int index)
{
    string receptionistpassword;
    cin >> receptionistpassword;
    int count = 3;
    if (arrReceptionists[index].password == "TBA")
    {
        arrReceptionists[index].password = receptionistpassword;
        return 3;
    }
    else if (arrReceptionists[index].password != receptionistpassword)
    {
        int valid = 0;
        while (valid == 0)
        {
            cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl
                << "\r|                              Hello Receptionists                              |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                          please insert your password                          |" << endl
                << "\r|         Invalid login, you have " << count << " more tries to retry           |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|Input:                                                                         |" << endl
                << "\r_________________________________________________________________________________" << endl;
            cout << "\033[1A" << "       ";
            cin >> receptionistpassword;
            if (arrReceptionists[index].password != receptionistpassword)
                valid = 0;
            else { valid = 1; }
            count--;
            if (count == 0)
            {
                return 1;

            }

        }

    }
    else {
        return 3;
    }
}

int HanaTamer(manager m, receptionist arrReceptionists[]) { // main function
    int role;
    cout << arrReceptionists[0].password;

    cout << "\033[40A" << "\r --------------------------------------------------------------------------------------" << endl
        << "\r|     ,--.                ,--.           ,--.        ,--.                  ,--.         |" << endl
        << "\r|     |  ,---.   ,---.  ,-'  '-.  ,---.  |  |      ,-|  | ,--.,--.  ,---.  |  |,-.      |" << endl
        << "\r|     |  .-.  | | .-. | '-.  .-' | .-. : |  |     ' .-. | |  ||  | (  .-'  |     /      |" << endl
        << "\r|     |  | |  | ' '-' '   |  |   \   --.  |  |    \ `-' |            .-'  `) |  \  \     |" << endl
        << "\r|     `--' `--'  `---'    `--'    `----' `--'      `---'   `----'  `----'  `--'`--'     |" << endl
        << "\r|---------------------------------------------------------------------------------------|" << endl
        << "\r|                                    1-Manager Login                                    |" << endl
        << "\r|                                  2-Receptionist Login                                 |" << endl
        << "\r|                                    3-Close Program                                    |" << endl
        << "\r|                                                                                       |" << endl
        << "\r|                                                                                       |" << endl
        << "\r|                                                                                       |" << endl
        << "\r|                                                                                       |" << endl
        << "\r|                                                                                       |" << endl
        << "\r _______________________________________________________________________________________" << endl;
    cout << "\033[2A" << " Input:      ";
    cin >> role;
    system("CLS");
    if (role == 1)
    {
        cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl
            << "\r|                                Welcome Manager                                |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                          please insert your password                          |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|Input:                                                                         |" << endl
            << "\r_________________________________________________________________________________" << endl;
        cout << "\033[1A" << "       ";
        cin >> m.password;
        int x = managerlogin(m.password);
        return x; // either title or manager dashboard
    }
    if (role == 2)
    {
        int index;
        cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl
            << "\r|                              Hello Receptionists                              |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                          please insert your Username                          |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                                             |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                        |" << endl
            << "\r______________________________________________________________________________________________" << endl;
        cout << "\033[1A" << "Input:        ";
        index = receptionistusernamelogin(arrReceptionists);
        if (index == -1) {
            return 1; //title
        }
        cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl
            << "\r|                              Hello Receptionists                              |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                        Now please insert your password                        |" << endl
            << "\r|                    (you create it now if you dont have one)                   |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r_________________________________________________________________________________" << endl;
        cout << "\033[2A" << "Input:                     ";
        int x = receptionistpasswordlogin(arrReceptionists, index);
        return x; //either title or receptionist dshboard

    }
    if (role == 3) {
        return 0;
    }
}

void listOfReceptionists(receptionist arr[], int numReceptionists) {
    cout << left << setw(6) << "index" << setw(6) << "ID"
        << setw(20) << "Name"
        << setw(20) << "Username"
        << setw(10) << "Salary" << endl;

    cout << string(56, '-') << endl;

    for (int i = 0; i < numReceptionists; i++) {
        cout << left << setw(6) << i << setw(6) << arr[i].ID
            << setw(20) << arr[i].name
            << setw(20) << arr[i].username
            << setw(10) << fixed << setprecision(2) << arr[i].salary << " EGP" << endl;
    }
}

void listOfWorkers(worker arr[], int numWorkers) {
    // table header
    cout << left << setw(6) << "index" << setw(6) << "ID"
        << setw(20) << "Name"
        << setw(15) << "Contact"
        << setw(30) << "Job Title"
        << setw(10) << "Salary" << endl;

    cout << string(81, '-') << endl;

    for (int i = 0; i < numWorkers; i++) {
        cout << left << setw(6) << i << setw(6) << arr[i].ID
            << setw(20) << arr[i].name
            << setw(15) << arr[i].contact
            << setw(30) << arr[i].JobTitle
            << setw(1) << arr[i].salary << "EGP" << endl;
    }
}

void deleteWorker(worker arr[], int& numWorkers, int INDEX) {
    // Ensure the INDEX is valid
    if (INDEX < 1 || INDEX > numWorkers) {
        cout << "Invalid index. No resident found.\n";
        return;
    }

    // Convert INDEX to the corresponding array position (0-based)
    int position = INDEX;

    // Shift residents to fill the gap
    for (int i = position; i < numWorkers - 1; i++) {
        arr[i] = arr[i + 1];
    }

    // Decrease the count of residents
    numWorkers--;

    cout << "worker at index " << INDEX << " deleted successfully.\n";
}

void deletereceptioist(receptionist arr[], int& numReceptionists, int INDEX) {
    // Ensure the INDEX is valid
    if (INDEX < 1 || INDEX > numReceptionists) {
        cout << "Invalid index. No resident found.\n";
        return;
    }

    // Convert INDEX to the corresponding array position (0-based)
    int position = INDEX;

    // Shift residents to fill the gap
    for (int i = position; i < numReceptionists - 1; i++) {
        arr[i] = arr[i + 1];
    }

    // Decrease the count of residents
    numReceptionists--;

    cout << "receptionist at index " << INDEX << " deleted successfully.\n";
}

worker* addworker(worker* list, int& size, worker value)
{
    worker* newList = new worker[size + 1]; // make new array
    for (int i = 0; i < size; i++)
    {
        newList[i] = list[i];
    }

    if (size)
    {
        delete[] list;
    }
    newList[size] = value;
    size++;
    return newList;
}

receptionist* addrecptionist(receptionist* list, int& size, receptionist value)
{
    receptionist* newList = new receptionist[size + 1]; // make new array
    for (int i = 0; i < size; i++)
    {
        newList[i] = list[i];
    }

    if (size)
    {
        delete[] list;
    }
    newList[size] = value;
    size++;
    return newList;
}

void editWorker(worker arrWorkers[], int workerID, int numWorkers) {
    char editManager; // Store user's choice
    do {
        cout << "Select the desired feature by pressing (1, 2, 3, 4, 5, or 6):\n";
        cout << "1. Edit Name\n";
        cout << "2. Edit ID\n";
        cout << "3. Edit Contact\n";
        cout << "4. Edit Job Title\n";
        cout << "5. Edit Salary\n";
        cout << "6. Exit\n";
        cin >> editManager;
        system("cls");

        switch (editManager) {
        case '1': { // Edit Name
            cout << "Enter new name: ";
            cin.ignore();
            getline(cin, arrWorkers[workerID].name);
            cout << "Name updated successfully!\n";
            listOfWorkers(arrWorkers, numWorkers);
            break;
        }
        case '2': { // Edit ID
            cout << "Enter new ID: ";
            cin >> arrWorkers[workerID].ID;
            cout << "ID updated successfully!\n";
            listOfWorkers(arrWorkers, numWorkers);
            break;
        }
        case '3': { // Edit Contact
            cout << "Enter new contact: ";
            cin.ignore();
            getline(cin, arrWorkers[workerID].contact);
            cout << "Contact updated successfully!\n";
            listOfWorkers(arrWorkers, numWorkers);
            break;
        }
        case '4': { // Edit Job Title
            cout << "Select a new job title from the following list:\n";
            const string jobTitles[] = { "Head Chef", "Director of Sales & Marketing", "Housekeeping", "Human Resources Director" };
            const int numJobTitles = sizeof(jobTitles) / sizeof(jobTitles[0]);

            for (int i = 0; i < numJobTitles; i++) {
                cout << i + 1 << ". " << jobTitles[i] << endl;
            }

            cout << "Enter the number corresponding to the new job title: ";
            int jobChoice;
            cin >> jobChoice;

            if (jobChoice < 1 || jobChoice > numJobTitles) {
                cout << "Invalid choice. Job Title update canceled.\n";
            }
            else {
                arrWorkers[workerID].JobTitle = jobTitles[jobChoice - 1];
                cout << "Job Title updated successfully to \"" << arrWorkers[workerID].JobTitle << "\"!\n";
            }
            system("cls");
            listOfWorkers(arrWorkers, numWorkers);
            break;
        }
        case '5': { // Edit Salary
            cout << "Enter new salary: ";
            cin >> arrWorkers[workerID].salary;
            cout << "Salary updated successfully!\n";
            listOfWorkers(arrWorkers, numWorkers);
            break;
        }
        case '6': { // Exit
            cout << "Exiting edit mode.\n";
            break;
        }
        default:
            cout << "Invalid selection. Please try again.\n";
        }
    } while (editManager != '6');
}

void editReceptionist(receptionist arr[], int receptionistID, int numReceptionists) {
    char editChoice;
    do {
        cout << "Select the desired feature by pressing (1, 2, 3, 4, or 5):\n";
        cout << "1. Edit Name\n";
        cout << "2. Edit ID\n";
        cout << "3. Edit Username\n";
        cout << "4. Edit Salary\n";
        cout << "5. Exit\n";
        cin >> editChoice;
        system("cls");

        switch (editChoice) {
        case '1': { // Edit Name
            cout << "Enter new name: ";
            cin.ignore();
            getline(cin, arr[receptionistID].name);
            cout << "Name updated successfully!\n";
            listOfReceptionists(arr, numReceptionists);
            break;
        }
        case '2': { // Edit ID
            cout << "Enter new ID: ";
            int newID;
            cin >> newID;

            // Check if the new ID already exists
            for (int i = 0; i < numReceptionists; i++) {
                if (i != receptionistID && arr[i].ID == newID) {
                    cout << "Error: ID already exists. Please use a unique ID.\n";
                    return;
                }
            }

            arr[receptionistID].ID = newID;
            cout << "ID updated successfully!\n";
            break;
        }
        case '3': { // Edit Username
            cout << "Enter new username: ";
            cin >> arr[receptionistID].username;
            cout << "Username updated successfully!\n";
            listOfReceptionists(arr, numReceptionists);
            break;
        }
        case '4': { // Edit Salary
            cout << "Enter new salary: ";
            cin >> arr[receptionistID].salary;
            cout << "Salary updated successfully!\n";
            listOfReceptionists(arr, numReceptionists);
            break;
        }
        case '5': { // Exit
            cout << "Exiting\n";
            break;
        }
        default:
            cout << "Invalid\n";
        }
    } while (editChoice != '5');
}

int Managerdashboard(worker arrWorkers[], receptionist arrReceptionists[], int maxWorkers, int maxReceptionists, int numWorkers, int numReceptionists)
{
    // manager dashboard
    cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl
        << "\r|                              Manager  Dashboard                               |" << endl
        << "\r|                                                                               |" << endl
        << "\r|                                                                               |" << endl
        << "\r|                                                                               |" << endl
        << "\r|                     Select the desired feature by pressing                    |" << endl
        << "\r|                              1) Worker Management                             |" << endl
        << "\r|                           2) Room Status Monitoring                           |" << endl
        << "\r|                               3) Income Reports                               |" << endl
        << "\r|                             4) back to title screen                           |" << endl
        << "\r|                                                                               |" << endl
        << "\r|                                                                               |" << endl
        << "\r|                                                                               |" << endl
        << "\r|                                                                               |" << endl
        << "\r|Input:                                                                         |" << endl
        << "\r_________________________________________________________________________________" << endl;
    cout << "\033[1A" << "       ";
    char choosetypeofworker;
    char managerSelect;
    cin >> managerSelect;
    system("cls");
    if (managerSelect == '1') // Worker Management
    {
        do {
            cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl
                << "\r|                              Manager  Dashboard                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                            Select the type to manage                          |" << endl
                << "\r|                                  1) Workers                                   |" << endl
                << "\r|                               2) Receptionists                                |" << endl
                << "\r|                                   3) Exit                                     |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|                                                                               |" << endl
                << "\r|Input:                                                                         |" << endl
                << "\r_________________________________________________________________________________" << endl;
            cout << "\033[1A" << "       ";
            cin >> choosetypeofworker;
            system("cls");
            if (choosetypeofworker == '1') {
                return 8;
            }
            else if (choosetypeofworker == '2') {
                return 9;
            }
            else if (choosetypeofworker == '3') {
                return 2;
            }
        } while (choosetypeofworker != '3');
    }
    else if (managerSelect == '2')
    {
        return 4;
    }
    else if (managerSelect == '3')
    {
        return 5;
    }
    else if (managerSelect == '4')
    {
        return 1;
    }
    else {
        cout << "Feature is not available";
    }

}

worker* workerflow(worker arrWorkers[], int& numWorkers, int& x) {
    worker w;
    int index = -1;
    // add new workers
    char choiceManager;
    do {
        system("cls");
        cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl;
        listOfWorkers(arrWorkers, numWorkers);
        cout << endl;

        cout << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                             Select the feature                                |" << endl
            << "\r|                               1) Add Worker                                   |" << endl
            << "\r|                           2) Edit Existing Worker                             |" << endl
            << "\r|                          3) Delete Existing Worker                            |" << endl
            << "\r|                                  4) Exit                                      |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|Input:                                                                         |" << endl
            << "\r_________________________________________________________________________________" << endl;
        cout << "\033[1A" << "       ";
        cin >> choiceManager;
        system("cls");
        switch (choiceManager) {
        case '1': {
            system("cls");
            listOfWorkers(arrWorkers, numWorkers);
            cout << "enter the new guys name : ";
            cin >> w.name;
            cout << endl << "enter the new guys salary : ";
            cin >> w.salary;
            cout << endl << "enter the new guys id : ";
            cin >> w.ID;
            cout << endl << "enter the new guys contact : ";
            cin >> w.contact;
            cout << endl << "enter the new guys Job Title : ";
            cout << endl << "1) Head Chef";
            cout << endl << "2) Director of Sales & Marketing";
            cout << endl << "3) Housekeeping";
            cout << endl << "4) Human Resources Director" << endl;
            char jobChoice;
            cin >> jobChoice;
            while (true) {
                switch (jobChoice) {
                case '1':
                    w.JobTitle = "Head Chef";
                    break;
                case '2':
                    w.JobTitle = "Director of Sales & Marketing";
                    break;
                case '3':
                    w.JobTitle = "Housekeeping";
                    break;
                case '4':
                    w.JobTitle = "Human Resources Director";
                    break;
                default:
                    continue;
                }
                break;
            }


            arrWorkers = addworker(arrWorkers, numWorkers, w);

            break;
        }

        case '2':
            system("cls");

            listOfWorkers(arrWorkers, numWorkers);

            int workerID;
            cout << "Please enter the workers id: ";
            cin >> workerID;

            // Find the worker by ID
            while (true) {
                for (int i = 0; i < numWorkers; i++) {
                    if (arrWorkers[i].ID == workerID) {
                        index = i;
                        break;
                    }
                }

                if (index == -1) {
                    system("CLS");
                    listOfWorkers(arrWorkers, numWorkers);

                    cout << "\r Enter the ID of the worker you want to edit:" << endl
                        << "\r Worker with ID " << workerID << " not found." << endl;
                    cin >> workerID;
                    continue;
                }
                break;
            }
            editWorker(arrWorkers, index, numWorkers);
            break;

        case '3': {
            system("ClS");
            listOfWorkers(arrWorkers, numWorkers);
            cout << " \r Enter the index of the worker you want to delete:" << endl;
            int deleteindex;
            cin >> deleteindex;
            deleteWorker(arrWorkers, numWorkers, deleteindex);
            break;
        }

        case '4':
            x = 2;
            return arrWorkers;

        default:
            cout << "Feature is not available\n";
        }
    } while (choiceManager != '4');
}

receptionist* receptionistflow(receptionist arrReceptionists[], int& numReceptionists, int& x) {
    // Receptionist Management
    char receptionistChoice;
    int receptionistindex = -1;
    receptionist r;

    // Initialize some receptionists by default (2)


    do {
        system("cls");

        cout << endl;
        cout << "\033[40A" << "\r -------------------------------------------------------------------------------" << endl;
        listOfReceptionists(arrReceptionists, numReceptionists);
        cout << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                           Select the desired feature                          |" << endl
            << "\r|                              1)Add Receptionist                               |" << endl
            << "\r|                             2) Delete Receptionist                                |" << endl
            << "\r|                              3) edit receptioist                                |" << endl
            << "\r|                                   4) Exit                                            |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|                                                                               |" << endl
            << "\r|Input:                                                                         |" << endl
            << "\r_________________________________________________________________________________" << endl;
        cout << "\033[1A" << "       ";
        cin >> receptionistChoice;
        system("cls");
        switch (receptionistChoice) {
        case '1':
            system("CLS");


            listOfReceptionists(arrReceptionists, numReceptionists);
            cout << "enter the new guys name : ";
            cin >> r.name;
            cout << endl << "enter the new guys username : ";
            cin >> r.username;
            cout << endl << "enter the new guys id : ";
            cin >> r.ID;
            cout << endl << "enter the new guys salary : ";
            cin >> r.salary;
            ;
            arrReceptionists = addrecptionist(arrReceptionists, numReceptionists, r);
            break;

        case '2':
            system("cls");

            listOfReceptionists(arrReceptionists, numReceptionists);

            int receptionstID;
            cout << "Please enter the workers id: ";
            cin >> receptionstID;

            // Find the worker by ID
            while (true) {
                for (int i = 0; i < numReceptionists; i++) {
                    if (arrReceptionists[i].ID == receptionstID) {
                        receptionistindex = i;
                        break;
                    }
                }




                if (receptionistindex == -1) {
                    system("CLS");
                    listOfReceptionists(arrReceptionists, numReceptionists);
                    cout << "\r Enter the ID of the worker you want to edit:" << endl;
                    cout << "\r Worker with ID " << receptionstID << " not found." << endl;
                    cin >> receptionstID;
                    continue;
                }
                break;
            }
            editReceptionist(arrReceptionists, receptionistindex, numReceptionists);
            break;
        case '3':
            system("ClS");
            listOfReceptionists(arrReceptionists, numReceptionists);
            cout << " \r Enter the index of the worker you want to delete:" << endl;
            int deleteindex;
            cin >> deleteindex;
            deletereceptioist(arrReceptionists, numReceptionists, deleteindex);
            break;
        case '4':
            x = 2;
            return arrReceptionists;

        default:
            cout << "Feature is not available\n";
        }
    } while (receptionistChoice != '4');
}

int occupation(room rooms[]) {


    system("CLS"); date x;
    dateinput(x);
    while (true) {
        dateinput(x);
        if (x.year < 2025) {
            cout << "thats an imposssible date to reserve";
            continue;
        }
        break;
    }

    for (int i = 0; i < 10; i++) {
        rooms[i].check.available = true;
        if (rooms[i].check.initial.year <= x.year && x.year <= rooms[i].check.final.year && rooms[i].check.initial.month <= x.month && x.month <= rooms[i].check.final.month && rooms[i].check.initial.day <= x.day && x.day <= rooms[i].check.final.day)
        {
            rooms[i].check.available = false; //occupied
            rooms[i].check.value = "occupied";
        }
        else {
            rooms[i].check.value = "not occupied";
        }
    }
    cout << "\033[40A" << "\r-------------------------------------------------------------------------------" << endl;

    cout << "|" << setw(6) << "Room ID : " << setw(18) << "Occupation state " << "|" << endl;
    for (int i = 0; i < 10; i++) {
        cout << "|" << setw(6) << rooms[i].ID << setw(18) << rooms[i].check.value << "|" << "\n";
    }
    cout << "\r                                                                            " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r                                                                               " << endl
        << "\r-------------------------------------------------------------------------------" << endl;

    char choice;
    while (true)
    {
        cout << endl << "are you done(y/n): " << endl;
        cin >> choice;

        if (choice == 'y' || choice == 'Y') {

        }
        else if (choice == 'n' || choice == 'N') {
            return 4;
        }
        else {
            cout << "invalid input";
            continue;
        }
        system("CLS");
        return 2;
    }
}

void listduration(date d) {
    cout << setw(3) << d.day << " day(s) "
        << setw(3) << d.month << " month(s)";
}

void listOfResidents(resident arr[], int noResidents) {
    // table header
    cout << left << setw(6) << "Index"
        << setw(20) << "Name"
        << setw(15) << "Contact"
        << setw(15) << "Room Type"
        << setw(24) << "Duration"
        << setw(30) << "Boarding Option" << endl;

    cout << string(95, '-') << endl;

    // Boarding option mapping
    string boardingOption[] = { "Invalid", "Full Board", "Half Board", "Bed & Breakfast" };

    for (int x = 0; x < noResidents; x++) {
        cout << left << setw(6) << (x)
            << setw(20) << arr[x].name
            << setw(15) << arr[x].contact
            << setw(15) << arr[x].RoomType
            << setw(20);
        listduration(arr[x].Staytime);
        cout << setw(4) << " ";
        // Display the boarding option as a string
        int boarding = arr[x].BoardingOption;
        if (boarding >= 1 && boarding <= 3) {
            cout << setw(20) << boardingOption[boarding] << endl;
        }
        else {
            cout << setw(20) << "Invalid Option" << endl;
        }
    }
}

void deleteResidents(resident arr[], int& noResidents, int INDEX) {
    // Ensure the INDEX is valid
    if (INDEX < 1 || INDEX > noResidents) {
        cout << "Invalid index. No resident found.\n";
        return;
    }

    // Convert INDEX to the corresponding array position (0-based)
    int position = INDEX;

    // Shift residents to fill the gap
    for (int i = position; i < noResidents - 1; i++) {
        arr[i] = arr[i + 1];
    }

    // Decrease the count of residents
    noResidents--;

    cout << "Resident at index " << INDEX << " deleted successfully.\n";
}

void editResident(resident arrResidents[], int residentIndex, int numResidents) {
    char editChoice; // Store user's choice
    do {
        cout << "Select the desired feature to edit by pressing (1, 2, 3, 4, 5, or 6):\n";
        cout << "1. Edit Name\n";
        cout << "2. Edit Contact\n";
        cout << "3. Edit Room Type\n";
        cout << "4. Edit Duration of Stay\n";
        cout << "5. Edit Boarding Option\n";
        cout << "6. Exit\n";
        cin >> editChoice;
        system("cls");

        switch (editChoice) {
        case '1': { // Edit Name
            cout << "Enter new name: ";
            cin.ignore();
            getline(cin, arrResidents[residentIndex].name);
            cout << "Name updated successfully!\n";
            listOfResidents(arrResidents, numResidents);
            break;
        }
        case '2': { // Edit Contact
            cout << "Enter new contact: ";
            cin >> arrResidents[residentIndex].contact;
            cout << "Contact updated successfully!\n";
            listOfResidents(arrResidents, numResidents);
            break;
        }
        case '3': { // Edit Room Type
            cout << "Select a new room type from the following list:\n";
            const string roomTypes[] = { "Single", "Double", "Triple" };
            const int numRoomTypes = sizeof(roomTypes) / sizeof(roomTypes[0]);

            for (int i = 0; i < numRoomTypes; i++) {
                cout << i + 1 << ". " << roomTypes[i] << endl;
            }

            cout << "Enter the number corresponding to the new room type: ";
            int roomChoice;
            cin >> roomChoice;

            if (roomChoice < 1 || roomChoice > numRoomTypes) {
                cout << "Invalid choice. Room Type update canceled.\n";
            }
            else {
                arrResidents[residentIndex].RoomType = roomTypes[roomChoice - 1];
                cout << "Room Type updated successfully to \"" << arrResidents[residentIndex].RoomType << "\"!\n";
            }
            listOfResidents(arrResidents, numResidents);
            break;
        }
        case '4': { // Edit Duration of Stay
            cout << "Enter new duration:\n";
            cout << "Days: ";
            cin >> arrResidents[residentIndex].Staytime.day;
            cout << "Months: ";
            cin >> arrResidents[residentIndex].Staytime.month;
            cout << "Duration updated successfully!\n";
            listOfResidents(arrResidents, numResidents);
            break;
        }
        case '5': { // Edit Boarding Option
            cout << "Select new Boarding Option:\n";
            cout << "1. Full Board\n2. Half Board\n3. Breakfast Only\n";
            int boardingChoice;
            cin >> boardingChoice;

            if (boardingChoice < 1 || boardingChoice > 3) {
                cout << "Invalid choice. Boarding option update canceled.\n";
            }
            else {
                arrResidents[residentIndex].BoardingOption = boardingChoice;
                cout << "Boarding Option updated successfully!\n";
            }
            listOfResidents(arrResidents, numResidents);
            break;
        }
        case '6': { // Exit
            cout << "Exiting edit mode.\n";
            break;
        }
        default:
            cout << "Invalid selection. Please try again.\n";
        }
    } while (editChoice != '6');
}

int ReceptionDashboard(resident arrResidents[], int& numResidents)
{
    // receptionist dashboard
    system("cls");
    cout << "Select the desired feature by pressing (1, 2 or 3)" << endl
        << "1) Resident Management" << endl << "2) Room Assignment" << endl << "3) Cost Calculaion" << endl << "4) Return to title" << endl;
    char receptionistSelect;
    cin >> receptionistSelect;
    system("cls");
    if (receptionistSelect == '1') // Resident Management
    {
        char choiceReceptionist;
        do {

            system("cls");
            listOfResidents(arrResidents, numResidents);
            cout << endl;
            cout << "1. Edit Existing Residents\n";
            cout << "2. Delete Existing Residents\n";
            cout << "3. Exit\n";

            cin >> choiceReceptionist;
            system("cls");
            switch (choiceReceptionist) {

            case '1':
                listOfResidents(arrResidents, numResidents);
                cout << "Enter the index of the resident you want to edit: ";
                int residentIndex;
                cin >> residentIndex;

                // index
                if (residentIndex < 0 || residentIndex >= numResidents) {
                    cout << "Error: Invalid index. No resident found.\n";
                    break;
                }

                // Edit the selected resident
                editResident(arrResidents, residentIndex, numResidents);
                break;


            case '2': {
                listOfResidents(arrResidents, numResidents);
                int deleteINDEX;
                cout << "Enter the index of the resident you want to delete: ";
                cin >> deleteINDEX;
                deleteResidents(arrResidents, numResidents, deleteINDEX);

                break;
            }

            case '3':
                system("cls");
                return 3;

            default:
                cout << "Feature is not available\n";
            }
        } while (choiceReceptionist != '3');
    }
    else if (receptionistSelect == '2')
    {
        return 7;
    }
    else if (receptionistSelect == '3')
    {
        return 6;
    }
    else if (receptionistSelect == '4')
    {
        return 1;
    }
    else {
        cout << "Feature is not available";
    }
}

int date_compare(date initial, date compare) {
    if (compare.year > initial.year || (compare.year == initial.year && compare.month > initial.month) || (compare.year == initial.year && compare.month == initial.month && compare.day > initial.day)) {
        return 1;
    }
    else if ((compare.year == initial.year) && (compare.month == initial.month) && (compare.day == initial.day)) {
        return 2;
    }
    else {
        return 3;
    }

}

resident* addresident(resident* list, int& size, resident value)
{
    resident* newList = new resident[size + 1]; // make new array
    for (int i = 0; i < size; i++)
    {
        newList[i] = list[i];
    }

    if (size)
    {
        delete[] list;
    }
    newList[size] = value;
    size++;
    return newList;
}

resident* reserve(room rooms[], resident residents[], int& residentsize, int& x) {
    int room_ID;
    int room_assign;
    int cost;
    int resident_id;
    resident r;
    bool flag_res_id = false;
    int count_res_id = 0;
    int telephone_number;
    int flag_reserve = 1;
    string name;
    while (flag_reserve == 1) {
        date arrival;
        date departure;
        date duration;
        flag_res_id = false;
        name.clear();
        system("cls");
        cout << "Please Enter Resident Name: ";
        cin.ignore();
        getline(cin, name);
        while (flag_res_id == false) {
            count_res_id = 0;
            cout << "Please Enter Resident ID: ";
            cin >> resident_id;
            if (resident_id == 001) {
                cout << "Invalid ID, Please Try Again" << endl;
                count_res_id += 1;
            }
            else {
                for (int i = 0; i < residentsize; i++) {
                    if (resident_id != residents[i].ID) {
                        continue;

                    }
                    else {
                        cout << "ID Taken, Please Try Again" << endl;
                        count_res_id += 1;
                    }

                }

            }
            if (count_res_id == 0) {
                flag_res_id = true;
            }
        }
        flag_res_id = false;
        while (flag_res_id == false) {
            count_res_id = 0;
            cout << "Please Enter Resident Telephone Number: ";
            cin >> telephone_number;
            for (int i = 0; i < residentsize; i++) {
                if (telephone_number == residents[i].contact) {
                    cout << "Telephone Number Taken, Try Again" << endl;
                    count_res_id += 1;
                }
            }
            if (count_res_id == 0) {
                flag_res_id = true;
            }
        }
        cout << "Please enter the date of your arrival: ";
        dateinput(arrival);
        cout << "Please enter the date of your departure: ";
        dateinput(departure);
        duration = durationCalc(arrival, departure);
        string occupy;

        for (int i = 0; i < 10; i++) {
            if ((date_compare(rooms[i].check.final, arrival) == 1 && date_compare(rooms[i].check.final, departure) == 1) ||
                (date_compare(rooms[i].check.initial, arrival) == 3 && date_compare(rooms[i].check.initial, departure) == 3))
            {
                rooms[i].check.available = 1;
            }
            else {
                rooms[i].check.available = 0;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (rooms[i].check.available == 1) {
                occupy = "Available";
            }
            else {
                occupy = "Occupied";
            }
            cout << std::setw(10) << rooms[i].ID
                << setw(15) << rooms[i].type << setw(15) << occupy << endl;
        }
        bool room_valid = 0;
        room_assign = 0;
        flag_res_id = 0;
        while (flag_res_id == false) {
            count_res_id = 0;
            cout << "Please Enter the Room ID to be assigned: ";
            cin >> room_ID;
            for (int i = 0; i < 10; i++) {
                if (rooms[i].ID == room_ID) {
                    room_valid = true;
                    if (rooms[i].check.available == 0) {
                        cout << "Room is Occupied, Please Choose another room";
                        count_res_id += 1;
                    }

                }

            }
            if (room_valid == false) {
                cout << "Invalid Input, Please Try Again";
                count_res_id += 1;
            }
            if (count_res_id == 0) {
                flag_res_id = true;
            }

        }
        int j;

        cost = cost_calculator(room_ID, duration);
        cout << "This room is assigned as " << boarding << " and is going to cost you " << cost << " L.E" << endl;
        flag_res_id = 0;
        if (boarding == "All Inclusive") {
            j = 1;
        }
        else if (boarding == "Half Board") {
            j = 2;
        }
        else {
            j = 3;
        }
        while (flag_res_id == false) {
            count_res_id = 0;
            cout << "Do you wish to continue: " << endl;
            cout << "Press 1 for Confirmation" << endl;
            cout << "Press 2 to Cancel" << endl;
            cin >> room_assign;

            if (room_assign == 1) {
                for (int i = 0; i < 10; i++) {
                    if (rooms[i].ID == room_ID) {
                        rooms[i].check.initial = arrival;
                        rooms[i].check.final = departure;
                        r.name = name;
                        r.contact = telephone_number;
                        r.ID = resident_id;
                        r.cost = cost;
                        r.Staytime = duration;
                        r.checkin = arrival;
                        r.checkout = departure;
                        r.BoardingOption = x;
                        r.RoomType = room_type;
                        r.ownroom = rooms[i];
                        residents = addresident(residents, residentsize, r);
                        flag_reserve = 1;
                        break;

                    }


                    else if (room_assign == 2) {
                        flag_reserve = 1;
                        flag_res_id = true;
                    }
                    else {
                        cout << "Invalid Input, Please Try Again";
                    }
                    if (count_res_id == 0) {
                        flag_res_id = true;
                        room_assign = 1;
                    }
                }

            }
            else if (room_assign == 2) {
                x = 3;
                return residents;
            }
            else {
                cout << "Invalid Input, Please Try Again";
            }

            char choice;
            while (true)
            {
                cout << endl << "are you done(y/n): " << endl;
                cin >> choice;

                if (choice == 'y' || choice == 'Y') {
                }
                else if (choice == 'n' || choice == 'N') {
                    x = 7;
                    return residents;
                }
                else {
                    cout << "invalid input";
                    continue;
                }
                x = 3;
                return residents;
            }
        }


    }
}

int main()
{
    int maxReception = 2;
    int maxworker = 2;
    int Residentnumber = 1;

    int numWorkers = 2;
    int numReceptionists = 2;
    resident* residents = new resident[Residentnumber];
    worker* workers = new worker[maxworker];
    receptionist* receptionists = new receptionist[maxReception];
    residents[0].cost = 160;
    residents[0].ID = 101;
    residents[0].contact = 1234567890;
    residents[0].name = "John Doe";
    residents[0].Staytime = durationCalc(residents[0].checkin, residents[0].checkout);
    residents[0].checkin = { 1, 1, 2025 };
    residents[0].checkout = { 6, 12, 2025 };
    residents[0].BoardingOption = 1; // Fullboard
    residents[0].RoomType = "Single";
    residents[0].ownroom = { 100.50, 202, "Single Room", {true} };



    room rooms[10] = {
    {0, 101, "Single", {true}},
    {0, 102, "Single", {true}},
    {0, 103, "Single", {true}},
    {0, 201, "Double", {true}},
    {0, 202, "Double", {true}},
    {0, 203, "Double", {true}},
    {0, 204, "Double", {true}},
    {0, 301, "Triple", {true}},
    {0, 302, "Triple", {true}},
    {0, 303, "Triple", {true}}
    };

    manager m;

    workers[0] = { 75000, 1, "01068361915", "Ahmed Mohamed", "Head Chef" };
    workers[1] = { 95000, 2, "01079608352", "Rodina Adel", "Director of Sales & Marketing" };

    receptionists[0] = { 35000, 1, "Abdelrahman Mohamed", "recp01" , "UMX" };
    receptionists[1] = { 70000, 2, "Nour Selim", "recp02" };
    cout << receptionists[0].password;
    int x = 1;
    while (true) {
        switch (x) {
        case 0:
            return 0;
            break;
        case 1:
            x = HanaTamer(m, receptionists);
            break;
        case 2:
            x = Managerdashboard(workers, receptionists, maxworker, maxReception, numWorkers, numReceptionists);
            break;
        case 3:
            x = ReceptionDashboard(residents, Residentnumber);
            break;
        case 4:
            x = occupation(rooms);
            break;
        case 5:
            x = IncomeTable(residents, workers, receptionists, Residentnumber, maxworker, maxReception);
            break;
        case 6:
            x = cost_calculatorSolo();
            break;
        case 7:
            residents = reserve(rooms, residents, Residentnumber, x);
            break;
        case 8:
            workers = workerflow(workers, maxworker, x);
            break;
        case 9:
            receptionists = receptionistflow(receptionists, maxReception, x);
            break;
        }
    }

}
