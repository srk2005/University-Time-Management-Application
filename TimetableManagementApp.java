import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class TimetableManagementApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }

    static class MainMenu extends JFrame {
        public MainMenu() {
            setTitle("Timetable Management - Main Menu");
            setSize(400, 250);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JButton adminButton = new JButton("Admin");
            JButton teacherButton = new JButton("Teacher");
            JButton studentButton = new JButton("Student");

            adminButton.addActionListener(e -> new AdminLoginWindow().setVisible(true));
            teacherButton.addActionListener(e -> new TeacherTimetableWindow().setVisible(true));
            studentButton.addActionListener(e -> new StudentWindow().setVisible(true));

            JPanel panel = new JPanel(new GridLayout(3, 1, 20, 20));
            panel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
            panel.add(adminButton);
            panel.add(teacherButton);
            panel.add(studentButton);

            add(panel);
        }
    }

    static class AdminLoginWindow extends JFrame {
        private static final String DUMMY_USERNAME = "admin";
        private static final String DUMMY_PASSWORD = "admin1";

        public AdminLoginWindow() {
            setTitle("Admin Login");
            setSize(350, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JTextField userField = new JTextField(15);
            JPasswordField passField = new JPasswordField(15);

            JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            fieldsPanel.add(new JLabel("Username:"));
            fieldsPanel.add(userField);
            fieldsPanel.add(new JLabel("Password:"));
            fieldsPanel.add(passField);

            JButton submitButton = new JButton("Submit");
            JButton clearButton = new JButton("Clear");

            submitButton.addActionListener(e -> {
                if (userField.getText().equals(DUMMY_USERNAME) && new String(passField.getPassword()).equals(DUMMY_PASSWORD)) {
                    new AdminDashboardWindow().setVisible(true);
                    dispose();
                }
                
                else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!");
                }
            });

            clearButton.addActionListener(e -> {userField.setText(""); passField.setText(""); });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(submitButton);
            buttonPanel.add(clearButton);

            setLayout(new BorderLayout(10, 10));
            add(fieldsPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    static class AdminDashboardWindow extends JFrame {
        public AdminDashboardWindow() {
            setTitle("Admin Dashboard");
            setSize(400, 250);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JButton classroomBtn = new JButton("Manage Classrooms");
            JButton courseBtn = new JButton("Manage Courses");

            classroomBtn.addActionListener(e -> new ClassroomDetailsWindow().setVisible(true));
            courseBtn.addActionListener(e -> new CourseManagementWindow().setVisible(true));

            JPanel panel = new JPanel(new GridLayout(2, 1, 20, 20));
            panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
            panel.add(classroomBtn);
            panel.add(courseBtn);

            add(panel);
        }
    }

    static class ClassroomDetailsWindow extends JFrame {
        public ClassroomDetailsWindow() {
            setTitle("Classroom Management");
            setSize(500, 400);
            setLocationRelativeTo(null);

            String[] columns = {"Room ID", "Capacity", "AV Equipment", "Computers", "Is Lab"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            JTable table = new JTable(model);

            JButton loadBtn = new JButton("Load");
            JButton addBtn = new JButton("Add");
            JButton saveBtn = new JButton("Save");

            loadBtn.addActionListener(e -> loadClassrooms(model));
            addBtn.addActionListener(e -> model.addRow(new Object[]{"", "", "", "", false}));
            saveBtn.addActionListener(e -> saveClassrooms(model));

            JPanel btnPanel = new JPanel();
            btnPanel.add(loadBtn);
            btnPanel.add(addBtn);
            btnPanel.add(saveBtn);

            add(new JScrollPane(table), BorderLayout.CENTER);
            add(btnPanel, BorderLayout.SOUTH);
        }

        private void loadClassrooms(DefaultTableModel model) {
            model.setRowCount(0);
            try (BufferedReader br = new BufferedReader(new FileReader("classrooms.csv"))) {
                String line;

                while ((line = br.readLine()) != null) {
                    model.addRow(line.split(","));
                }
            }
            
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading classrooms!");
            }
        }

        private void saveClassrooms(DefaultTableModel model) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("classrooms.csv"))) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    String line = String.join(",", model.getValueAt(i, 0).toString(),
                        model.getValueAt(i, 1).toString(),
                        model.getValueAt(i, 2).toString(),
                        model.getValueAt(i, 3).toString(),
                        model.getValueAt(i, 4).toString()
                    );

                    bw.write(line + "\n");
                }

                JOptionPane.showMessageDialog(this, "Saved successfully!");
            }
            
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage());
            }
        }
    }

    static class CourseManagementWindow extends JFrame {
        public CourseManagementWindow() {
            setTitle("Course Management");
            setSize(800, 400);
            setLocationRelativeTo(null);

            String[] columns = {"Course Code", "Instructor", "Classroom", "Day", "Time"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            JTable table = new JTable(model);

            JButton loadBtn = new JButton("Load");
            JButton addBtn = new JButton("Add");
            JButton saveBtn = new JButton("Save");

            loadBtn.addActionListener(e -> loadCourses(model));
            addBtn.addActionListener(e -> model.addRow(new Object[]{"", "", "", "", ""}));
            saveBtn.addActionListener(e -> saveCourses(model));

            JPanel btnPanel = new JPanel();
            btnPanel.add(loadBtn);
            btnPanel.add(addBtn);
            btnPanel.add(saveBtn);

            add(new JScrollPane(table), BorderLayout.CENTER);
            add(btnPanel, BorderLayout.SOUTH);
        }

        private void loadCourses(DefaultTableModel model) {
            model.setRowCount(0);
            try (BufferedReader br = new BufferedReader(new FileReader("courses.csv"))) {
                String line;

                while ((line = br.readLine()) != null) {
                    model.addRow(line.split(","));
                }
            }
            
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading courses!");
            }
        }

        private void saveCourses(DefaultTableModel model) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("courses.csv"))) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    String line = String.join(",", model.getValueAt(i, 0).toString(),
                        model.getValueAt(i, 1).toString(),
                        model.getValueAt(i, 2).toString(),
                        model.getValueAt(i, 3).toString(),
                        model.getValueAt(i, 4).toString()
                    );

                    bw.write(line + "\n");
                }

                JOptionPane.showMessageDialog(this, "Courses saved successfully!");
            }
            
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving courses: " + ex.getMessage());
            }
        }
    }

    static class TeacherTimetableWindow extends JFrame {
        private JComboBox<String> teacherComboBox;
        private DefaultTableModel model;
        private JTable table;
        private java.util.List<TimeSlotInfo> timeSlotsInfo;

        public TeacherTimetableWindow() {
            setTitle("Teacher Weekly Timetable");
            setSize(1000, 600);
            setLocationRelativeTo(null);

            teacherComboBox = new JComboBox<>();
            JPanel controlPanel = new JPanel(new FlowLayout());
            controlPanel.add(new JLabel("Select Teacher:"));
            controlPanel.add(teacherComboBox);

            timeSlotsInfo = getAllTimeSlotsInfo();
            java.util.List<String> timeSlots = new ArrayList<>();

            for (TimeSlotInfo tsi : timeSlotsInfo) {
                timeSlots.add(tsi.slotString);
            }

            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            model = new DefaultTableModel();
            model.addColumn("Time");

            for (String day : days) {
                 model.addColumn(day);
            }

            table = new JTable(model);
            table.setRowHeight(80);
            table.setDefaultRenderer(Object.class, new TimetableCellRenderer());
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
            table.getTableHeader().setBackground(new Color(200, 220, 200));
            table.setFont(new Font("Arial", Font.PLAIN, 13));

            teacherComboBox.addActionListener(e -> loadTeacherWeeklyTimetable());

            add(controlPanel, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);

            loadTeachers();

            if (teacherComboBox.getItemCount() > 0) {
                teacherComboBox.setSelectedIndex(0);
                loadTeacherWeeklyTimetable();
            }
        }

        private java.util.List<TimeSlotInfo> getAllTimeSlotsInfo() {
            java.util.List<TimeSlotInfo> slots = new ArrayList<>();
            String[] slotStrings = {"8:00AM-8:50AM", "9:00AM-9:50AM", "10:00AM-10:50AM",
                "11:00AM-11:50AM", "12:00PM-12:50PM", "1:00PM-1:50PM",
                "2:00PM-2:50PM", "3:00PM-3:50PM", "4:00PM-4:50PM",
                "5:00PM-5:50PM", "6:00PM-6:50PM"
            };

            for (String slot : slotStrings) {
                String[] parts = slot.split("-");
                int start = convertToMinutes(parts[0]);
                int end = convertToMinutes(parts[1]);
                slots.add(new TimeSlotInfo(slot, start, end));
            }

            return slots;
        }

        private int convertToMinutes(String timeStr) {
            try {
                boolean hasMeridian = timeStr.toUpperCase().contains("AM") || timeStr.toUpperCase().contains("PM");
                boolean isPM = timeStr.toUpperCase().contains("PM");
                boolean isAM = timeStr.toUpperCase().contains("AM");

                String cleanTime = timeStr.replaceAll("[^0-9:]", "");
                String[] timeParts = cleanTime.split(":");
                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);

                if (!hasMeridian) {
                    if (hours >= 1 && hours <= 7) {
                        isPM = true;
                    }
                }

                if (isPM && hours < 12) {
                     hours += 12;
                }

                if ((isAM || !isPM) && hours == 12) {
                     hours = 0;
                }

                return hours * 60 + minutes;
            }
            
            
            catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        private void loadTeachers() {
            Set<String> teachers = new HashSet<>();
            try (BufferedReader br = new BufferedReader(new FileReader("courses.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts.length >= 2) {
                         teachers.add(parts[1].trim());
                    }
                }
            }
            
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading teachers!");
            }

            teacherComboBox.removeAllItems();
            teachers.forEach(teacherComboBox::addItem);
        }

        private void loadTeacherWeeklyTimetable() {
            String selectedTeacher = (String) teacherComboBox.getSelectedItem();
            if (selectedTeacher == null) {
                 return;
            }

            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            Map<String, Map<String, java.util.List<String>>> timetableData = new HashMap<>();
            for (String day : days) {
                timetableData.put(day, new HashMap<>());
            }

            try (BufferedReader br = new BufferedReader(new FileReader("courses.csv"))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts.length >= 5 && parts[1].trim().equals(selectedTeacher)) {
                        String courseCode = parts[0].trim();
                        String classroom = parts[2].trim();
                        String day = parts[3].trim();
                        String timeRange = parts[4].trim();

                        java.util.List<TimeSegment> segments = splitIntoHourSegments(timeRange);
                        for (TimeSegment segment : segments) {
                            for (TimeSlotInfo slot : timeSlotsInfo) {
                                if (isTimeInSlot(segment.start, segment.end, slot)) {
                                    String classInfo = String.format("<html><b>%s</b><br>%s<br>%s</html>",
                                            courseCode, classroom, formatTimeRange(segment.start, segment.end));
                                    timetableData.putIfAbsent(day, new HashMap<>());
                                    timetableData.get(day).computeIfAbsent(slot.slotString, k -> new ArrayList<>())
                                               .add(classInfo);
                                }
                            }
                        }
                    }
                }

            }
            
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading timetable: " + e.getMessage());
            }

            model.setRowCount(0);
            for (TimeSlotInfo tsi : timeSlotsInfo) {
                Object[] row = new Object[days.length + 1];
                row[0] = tsi.slotString;

                for (int i = 0; i < days.length; i++) {
                    String day = days[i];
                    java.util.List<String> classes = timetableData.get(day).get(tsi.slotString);

                    if (classes != null && !classes.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (String s : classes) {
                             sb.append(s).append("<br>");
                        }
                        row[i + 1] = "<html>" + sb.toString() + "</html>";
                    }
                    
                    else {
                        row[i + 1] = "";
                    }
                }
                model.addRow(row);
            }
        }

        private java.util.List<TimeSegment> splitIntoHourSegments(String timeRange) {
            java.util.List<TimeSegment> segments = new ArrayList<>();
            try {
                String[] parts = timeRange.split("-");
                int start = convertToMinutes(parts[0]);
                int end = convertToMinutes(parts[1]);

                if (end <= start) {
                     end += 24 * 60;
                }

                for (int currentStart = start; currentStart < end; currentStart += 60) {
                    int currentEnd = Math.min(currentStart + 60, end);
                    segments.add(new TimeSegment(
                        currentStart % (24 * 60),
                        currentEnd % (24 * 60)
                    ));
                }
            }
            
            catch (Exception e) {
                e.printStackTrace();
            }
            return segments;
        }

        private boolean isTimeInSlot(int start, int end, TimeSlotInfo slot) {
            int slotStart = slot.start;
            int slotEnd = slot.end;

            if (slotEnd <= slotStart) {
                 slotEnd += 24 * 60;
            }

            if (end <= start) {
                 end += 24 * 60;
            }

            return (start < slotEnd) && (end > slotStart);
        }

        private String formatTimeRange(int start, int end) {
            return formatTime(start) + " - " + formatTime(end);
        }

        private String formatTime(int minutes) {
            int hours = minutes / 60;
            int mins = minutes % 60;
            String period = hours < 12 ? "AM" : "PM";

            if (hours > 12) {
                 hours -= 12;
            }

            if (hours == 0) {
                 hours = 12;
            }

            return String.format("%d:%02d%s", hours, mins, period);
        }

        class TimeSlotInfo {
            String slotString;
            int start;
            int end;

            TimeSlotInfo(String slotString, int start, int end) {
                this.slotString = slotString;
                this.start = start;
                this.end = end;
            }
        }

        class TimeSegment {
            int start;
            int end;

            TimeSegment(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }

        class TimetableCellRenderer extends DefaultTableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                Component c = super.getTableCellRendererComponent(
                                   table, value, isSelected, hasFocus, row, column);
                if (column == 0) {
                    setBackground(new Color(240, 240, 240));
                    setHorizontalAlignment(CENTER);
                }
                
                else {
                    if (value != null && !value.toString().isEmpty()) {
                        setBackground(new Color(220, 240, 220));
                    } else {
                        setBackground(Color.WHITE);
                    }
                    setHorizontalAlignment(CENTER);
                }

                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                return c;
            }
        }
    }

    static class StudentWindow extends JFrame {
        public StudentWindow() {
            setTitle("Student Portal");
            setSize(400, 250);
            setLocationRelativeTo(null);

            JButton coursesBtn = new JButton("Manage Courses");
            JButton timetableBtn = new JButton("Show Timetable");

            coursesBtn.addActionListener(e -> new ManageCoursesWindow().setVisible(true));
            timetableBtn.addActionListener(e -> new ViewTimetableWindow().setVisible(true));

            JPanel panel = new JPanel(new GridLayout(2, 1, 20, 20));
            panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
            panel.add(coursesBtn);
            panel.add(timetableBtn);

            add(panel);
        }
    }

    static class ManageCoursesWindow extends JFrame {
        public ManageCoursesWindow() {
            setTitle("Course Management");
            setSize(800, 400);
            setLocationRelativeTo(null);

            String[] columns = {"Select", "Course Code", "Instructor", "Classroom", "Day", "Time"};
            DefaultTableModel model = new DefaultTableModel(columns, 0) {
                @Override
                public Class<?> getColumnClass(int column) {
                    if (column == 0) {
                         return Boolean.class;
                    }
                    return String.class;
                }
            };

            JTable courseTable = new JTable(model);

            try (BufferedReader br = new BufferedReader(new FileReader("courses.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        model.addRow(new Object[]{false, parts[0], parts[1], parts[2], parts[3], parts[4]});
                    }
                }
            }
            
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading courses!");
            }

            JButton selectAllBtn = new JButton("Select All");
            JButton saveBtn = new JButton("Save Timetable");
            JButton checkConflictsBtn = new JButton("Check Conflicts");

            selectAllBtn.addActionListener(e -> {for (int i = 0; i < model.getRowCount(); i++) {model.setValueAt(true, i, 0);}});
            saveBtn.addActionListener(e -> saveSelection(courseTable, model));
            checkConflictsBtn.addActionListener(e -> checkConflicts(model));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(selectAllBtn);
            buttonPanel.add(saveBtn);
            buttonPanel.add(checkConflictsBtn);

            add(new JScrollPane(courseTable), BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private void saveSelection(JTable table, DefaultTableModel model) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("timetable.csv"))) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    Boolean selected = (Boolean) model.getValueAt(i, 0);

                    if (selected != null && selected) {
                        String courseCode = model.getValueAt(i, 1).toString();
                        String instructor = model.getValueAt(i, 2).toString();
                        String classroom = model.getValueAt(i, 3).toString();
                        String day = model.getValueAt(i, 4).toString();
                        String time = model.getValueAt(i, 5).toString();
                        String line = String.join(",", courseCode, instructor, classroom, day, time);
                        bw.write(line + "\n");
                    }
                }
                JOptionPane.showMessageDialog(this, "Timetable saved!");
            }
            
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving timetable: " + e.getMessage());
            }
        }

        private void checkConflicts(DefaultTableModel model) {
            Map<String, java.util.List<TimeSlot>> dayTimeMap = new HashMap<>();
            StringBuilder conflictMsg = new StringBuilder();

            for (int i = 0; i < model.getRowCount(); i++) {
                Boolean selected = (Boolean) model.getValueAt(i, 0);

                if (selected != null && selected) {
                    String courseCode = model.getValueAt(i, 1).toString();
                    String day = model.getValueAt(i, 4).toString();
                    String timeSlot = model.getValueAt(i, 5).toString();

                    if (day.isEmpty() || timeSlot.isEmpty()) {
                        continue;
                    }

                    TimeSlot slot = new TimeSlot(courseCode, timeSlot);

                    if (!dayTimeMap.containsKey(day)) {
                        dayTimeMap.put(day, new ArrayList<>());
                    }
                    dayTimeMap.get(day).add(slot);
                }
            }

            for (String day : dayTimeMap.keySet()) {
                java.util.List<TimeSlot> slots = dayTimeMap.get(day);
                for (int i = 0; i < slots.size(); i++) {
                    for (int j = i + 1; j < slots.size(); j++) {
                        if (slots.get(i).overlaps(slots.get(j))) {
                            conflictMsg.append("Conflict on ").append(day)
                                       .append(": ").append(slots.get(i).courseCode)
                                      .append(" and ").append(slots.get(j).courseCode)
                                      .append(" at ").append(slots.get(i).timeStr)
                                      .append(" and ").append(slots.get(j).timeStr)
                                      .append("\n");
                        }
                    }
                }
            }

            if (conflictMsg.length() > 0) {
                JOptionPane.showMessageDialog(this,
                    "Found these conflicts:\n" + conflictMsg.toString(),
                    "Timetable Conflicts", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No conflicts found in your selection!", "Conflict Check", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    static class TimeSlot {
        String courseCode;
        String timeStr;
        int startMinutes;
        int endMinutes;

        public TimeSlot(String courseCode, String timeStr) {
            this.courseCode = courseCode;
            this.timeStr = timeStr;
            parseTime(timeStr);
        }

        private void parseTime(String time) {
            try {
                String[] parts = time.split("-");
                startMinutes = convertToMinutes(parts[0]);
                endMinutes = convertToMinutes(parts[1]);
            }
            
            catch (Exception e) {
                startMinutes = 0;
                endMinutes = 0;
            }
        }

        private int convertToMinutes(String timeStr) {
            try {
                boolean hasMeridian = timeStr.toUpperCase().contains("AM") || timeStr.toUpperCase().contains("PM");
                boolean isPM = timeStr.toUpperCase().contains("PM");
                boolean isAM = timeStr.toUpperCase().contains("AM");

                String cleanTime = timeStr.replaceAll("[^0-9:]", "");
                String[] timeParts = cleanTime.split(":");
                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);

                if (!hasMeridian) {
                    if (hours >= 1 && hours <= 7) {
                        isPM = true;
                    }
                }

                if (isPM && hours < 12) {
                     hours += 12;
                }
                if ((isAM || !isPM) && hours == 12) {
                     hours = 0;
                }

                return hours * 60 + minutes;
            } catch (Exception e) {
                return 0;
            }
        }

        public boolean overlaps(TimeSlot other) {
            return this.startMinutes < other.endMinutes &&
                   other.startMinutes < this.endMinutes;
        }
    }

    static class ViewTimetableWindow extends JFrame {
        public ViewTimetableWindow() {
            setTitle("Weekly Timetable");
            setSize(1000, 600);
            setLocationRelativeTo(null);

            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            java.util.List<TimeSlotInfo> timeSlotsInfo = getAllTimeSlotsInfo();
            java.util.List<String> timeSlots = new ArrayList<>();
            for (TimeSlotInfo tsi : timeSlotsInfo) {
                timeSlots.add(tsi.slotString);
            }

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Time");
            for (String day : days) {
                 model.addColumn(day);
            }

            Map<String, Map<String, java.util.List<String>>> timetableData = new HashMap<>();
            for (String day : days) {
                 timetableData.put(day, new HashMap<>());
            }

            try (BufferedReader br = new BufferedReader(new FileReader("timetable.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        String courseCode = parts[0].trim();
                        String instructor = parts[1].trim();
                        String classroom = parts[2].trim();
                        String day = parts[3].trim();
                        String timeRange = parts[4].trim();

                        java.util.List<TimeSegment> segments = splitIntoHourSegments(timeRange);
                        for (TimeSegment segment : segments) {
                            for (TimeSlotInfo slot : timeSlotsInfo) {
                                if (isTimeInSlot(segment.start, segment.end, slot)) {
                                    String classInfo = String.format("<html><b>%s</b><br>%s<br>%s<br>%s</html>", courseCode, instructor, classroom, formatTimeRange(segment.start, segment.end));
                                    timetableData.putIfAbsent(day, new HashMap<>());
                                    timetableData.get(day).computeIfAbsent(slot.slotString, k -> new ArrayList<>()).add(classInfo);
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading timetable: " + e.getMessage());
            }

            model.setRowCount(0);
            for (TimeSlotInfo tsi : timeSlotsInfo) {
                Object[] row = new Object[days.length + 1];
                row[0] = tsi.slotString;
                for (int i = 0; i < days.length; i++) {
                    String day = days[i];
                    java.util.List<String> classes = timetableData.get(day).get(tsi.slotString);

                    if (classes != null && !classes.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (String s : classes) {
                             sb.append(s).append("<br>");
                        }
                        row[i + 1] = "<html>" + sb.toString() + "</html>";
                    } else {
                        row[i + 1] = "";
                    }
                }
                model.addRow(row);
            }

            JTable table = new JTable(model);
            table.setRowHeight(80);
            table.setDefaultRenderer(Object.class, new TimetableCellRenderer());
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
            table.getTableHeader().setBackground(new Color(200, 220, 200));
            table.setFont(new Font("Arial", Font.PLAIN, 13));

            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);
        }

        private java.util.List<TimeSegment> splitIntoHourSegments(String timeRange) {
            java.util.List<TimeSegment> segments = new ArrayList<>();
            try {
                String[] parts = timeRange.split("-");
                int start = convertToMinutes(parts[0]);
                int end = convertToMinutes(parts[1]);

                if (end <= start) {
                     end += 24 * 60;
                }
                for (int currentStart = start; currentStart < end; currentStart += 60) {
                    int currentEnd = Math.min(currentStart + 60, end);
                    segments.add(new TimeSegment(currentStart % (24 * 60), currentEnd % (24 * 60)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return segments;
        }

        private boolean isTimeInSlot(int start, int end, TimeSlotInfo slot) {
            int slotStart = slot.start;
            int slotEnd = slot.end;

            if (slotEnd <= slotStart) {
                 slotEnd += 24 * 60;
            }
            if (end <= start) {
                 end += 24 * 60;
            }

            return (start < slotEnd) && (end > slotStart);
        }

        private String formatTimeRange(int start, int end) {
            return formatTime(start) + " - " + formatTime(end);
        }

        private String formatTime(int minutes) {
            int hours = minutes / 60;
            int mins = minutes % 60;
            String period = hours < 12 ? "AM" : "PM";
            if (hours > 12) {
                 hours -= 12;
            }
            if (hours == 0) {
                 hours = 12;
            }
            return String.format("%d:%02d%s", hours, mins, period);
        }

        private java.util.List<TimeSlotInfo> getAllTimeSlotsInfo() {
            java.util.List<TimeSlotInfo> slots = new ArrayList<>();
            String[] slotStrings = {"8:00AM-8:50AM", "9:00AM-9:50AM", "10:00AM-10:50AM",
                "11:00AM-11:50AM", "12:00PM-12:50PM", "1:00PM-1:50PM",
                "2:00PM-2:50PM", "3:00PM-3:50PM", "4:00PM-4:50PM",
                "5:00PM-5:50PM", "6:00PM-6:50PM"
            };
            for (String slot : slotStrings) {
                String[] parts = slot.split("-");
                int start = convertToMinutes(parts[0]);
                int end = convertToMinutes(parts[1]);
                slots.add(new TimeSlotInfo(slot, start, end));
            }
            return slots;
        }

        private int convertToMinutes(String timeStr) {
            try {
                boolean hasMeridian = timeStr.toUpperCase().contains("AM") || timeStr.toUpperCase().contains("PM");
                boolean isPM = timeStr.toUpperCase().contains("PM");
                boolean isAM = timeStr.toUpperCase().contains("AM");

                String cleanTime = timeStr.replaceAll("[^0-9:]", "");
                String[] timeParts = cleanTime.split(":");
                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);

                if (!hasMeridian) {
                    if (hours >= 1 && hours <= 7) {
                        isPM = true;
                    }
                }

                if (isPM && hours < 12) {
                     hours += 12;
                }
                if ((isAM || !isPM) && hours == 12) {
                     hours = 0;
                }

                return hours * 60 + minutes;
            } catch (Exception e) {
                return 0;
            }
        }

        class TimeSlotInfo {
            String slotString;
            int start;
            int end;

            TimeSlotInfo(String slotString, int start, int end) {
                this.slotString = slotString;
                this.start = start;
                this.end = end;
            }
        }

        class TimetableCellRenderer extends DefaultTableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                if (column == 0) {
                    setBackground(new Color(240, 240, 240));
                    setHorizontalAlignment(CENTER);
                } else {
                    if (value != null && !value.toString().isEmpty()) {
                        setBackground(new Color(220, 240, 220));
                    } else {
                        setBackground(Color.WHITE);
                    }
                    setHorizontalAlignment(CENTER);
                }
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                return c;
            }
        }

        class TimeSegment {
            int start;
            int end;

            TimeSegment(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
    }
}