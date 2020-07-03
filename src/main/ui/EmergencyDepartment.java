package ui;

import model.rooms.*;
import ui.tools.BlankTool;
import ui.tools.applicationtools.LoadTool;
import ui.tools.applicationtools.QuitTool;
import ui.tools.applicationtools.SaveTool;
import ui.tools.patienttools.*;
import ui.tools.stafftools.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static model.rooms.Room.allRooms;

/**
 * Emergency Department Application
 */

public class EmergencyDepartment extends JFrame {

    public static final int WIDTH = 1790;
    public static final int HEIGHT = 800;

    private QuitTool quitTool;
    private SaveTool saveTool;
    private LoadTool loadTool;
    private BlankTool blankTool1;
    private BlankTool blankTool2;
    private BlankTool blankTool3;
    private BlankTool blankTool4;
    private BlankTool blankTool5;
    private AssignPatientToRoomTool assignPatientToRoomTool;
    private ChangePatientUrgencyTool changePatientUrgencyTool;
    private DischargePatientTool dischargePatientTool;
    private PatientCheckInTool patientCheckInTool;
    private ShowAllCheckedInPatientsTool showAllCheckedInPatientsTool;
    private ShowNextPatientNeedsRoomTool showNextPatientNeedsRoomTool;
    private AssignNurseToRoomTool assignNurseToRoomTool;
    private ShowAllClockedInStaffTool showAllClockedInStaffTool;
    private ShowOneNurseRoomsTool showOneNurseRoomsTool;
    private ShowOneRoomAllInfoTool showOneRoomAllInfoTool;
    private StaffClockInTool staffClockInTool;
    private StaffClockOutTool staffClockOutTool;
    private ShowAllOnePatientInfoTool showAllOnePatientInfoTool;

    // EFFECTS: Runs ED application
    public EmergencyDepartment() {
        super("Emergency Department");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initializeRooms();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this EmergencyDepartment will operate,
    //          populates tools to be used to manipulate the state of the application
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        initializeTools();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: declares and instantiates all tools
    private void initializeTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0, 7));
        add(toolArea, BorderLayout.EAST);
        createTools(toolArea);

    }

    // MODIFIES: this
    // EFFECTS: Declares & instantiates all tools
    private void createTools(JPanel toolArea) {
        patientCheckInTool = new PatientCheckInTool(this, toolArea);
        showNextPatientNeedsRoomTool = new ShowNextPatientNeedsRoomTool(this, toolArea);
        showAllCheckedInPatientsTool = new ShowAllCheckedInPatientsTool(this, toolArea);
        assignPatientToRoomTool = new AssignPatientToRoomTool(this, toolArea);
        changePatientUrgencyTool = new ChangePatientUrgencyTool(this, toolArea);
        dischargePatientTool = new DischargePatientTool(this, toolArea);
        showAllOnePatientInfoTool = new ShowAllOnePatientInfoTool(this, toolArea);
        showOneRoomAllInfoTool = new ShowOneRoomAllInfoTool(this, toolArea);
        assignNurseToRoomTool = new AssignNurseToRoomTool(this, toolArea);
        showOneNurseRoomsTool = new ShowOneNurseRoomsTool(this, toolArea);
        showAllClockedInStaffTool = new ShowAllClockedInStaffTool(this, toolArea);
        staffClockInTool = new StaffClockInTool(this, toolArea);
        staffClockOutTool = new StaffClockOutTool(this, toolArea);
        blankTool1 = new BlankTool(this, toolArea);
        loadTool = new LoadTool(this, toolArea);
        saveTool = new SaveTool(this, toolArea);
        quitTool = new QuitTool(this, toolArea);
        blankTool2 = new BlankTool(this, toolArea);
        blankTool3 = new BlankTool(this, toolArea);
        blankTool4 = new BlankTool(this, toolArea);
        blankTool5 = new BlankTool(this, toolArea);
    }

    // MODIFIES: allRooms
    // EFFECTS: Initializes rooms
    private void initializeRooms() {
        initializeRooms101to106();
        initializeRooms107to114();
        initializeRooms115to122();
        initializeRooms123to129();
        initializeRooms130to139();
        initializeRooms140to144();
        initializeRooms145to150();
        initializeRooms151to155();
    }

    // MODIFIES: allRooms
    // EFFECTS: initializes rooms 101-106
    public void initializeRooms101to106() {
        Room room101 = new Room(101);
        List<Capabilities> room101Capabilities = new ArrayList<>();
        room101Capabilities.add(Capabilities.Nitrous_Oxide);
        room101.setCapabilities(room101Capabilities);
        allRooms.add(room101);
        Room room102 = new Room(102);
        allRooms.add(room102);
        Room room103 = new Room(103);
        allRooms.add(room103);
        Room room104 = new Room(104);
        allRooms.add(room104);
        Room room105 = new Room(105);
        allRooms.add(room105);
        Room room106 = new Room(106);
        List<Capabilities> room106Capabilities = new ArrayList<>();
        room106Capabilities.add(Capabilities.Trauma);
        room106Capabilities.add(Capabilities.Nitrous_Oxide);
        room106.setCapabilities(room106Capabilities);
        allRooms.add(room106);
    }

    // MODIFIES: allRooms
    // EFFECTS: initializes rooms 107-114
    public void initializeRooms107to114() {
        Room room107 = new Room(107);
        List<Capabilities> room107Capabilities = new ArrayList<>();
        room107Capabilities.add(Capabilities.Trauma);
        room107Capabilities.add(Capabilities.Nitrous_Oxide);
        room107.setCapabilities(room107Capabilities);
        allRooms.add(room107);
        Room room108 = new Room(108);
        allRooms.add(room108);
        Room room109 = new Room(109);
        allRooms.add(room109);
        Room room110 = new Room(110);
        List<Capabilities> room110Capabilities = new ArrayList<>();
        room110Capabilities.add(Capabilities.Isolation);
        room110.setCapabilities(room110Capabilities);
        allRooms.add(room110);
        Room room111 = new Room(111);
        allRooms.add(room111);
        Room room112 = new Room(112);
        allRooms.add(room112);
        Room room113 = new Room(113);
        allRooms.add(room113);
        Room room114 = new Room(114);
        allRooms.add(room114);
    }

    // MODIFIES: allRooms
    // EFFECTS: initializes rooms 115-122
    public void initializeRooms115to122() {
        Room room115 = new Room(115);
        allRooms.add(room115);
        Room room116 = new Room(116);
        allRooms.add(room116);
        Room room117 = new Room(117);
        List<Capabilities> room117Capabilities = new ArrayList<>();
        room117Capabilities.add(Capabilities.Nitrous_Oxide);
        room117.setCapabilities(room117Capabilities);
        allRooms.add(room117);
        Room room118 = new Room(118);
        allRooms.add(room118);
        Room room119 = new Room(119);
        List<Capabilities> room119Capabilities = new ArrayList<>();
        room119Capabilities.add(Capabilities.Nitrous_Oxide);
        room119.setCapabilities(room119Capabilities);
        allRooms.add(room119);
        Room room120 = new Room(120);
        allRooms.add(room120);
        Room room121 = new Room(121);
        allRooms.add(room121);
        Room room122 = new Room(122);
        allRooms.add(room122);
    }

    // MODIFIES: allRooms
    // EFFECTS: initializes rooms 123-129
    public void initializeRooms123to129() {
        Room room123 = new Room(123);
        List<Capabilities> room123Capabilities = new ArrayList<>();
        room123Capabilities.add(Capabilities.Mental_Wellness);
        room123.setCapabilities(room123Capabilities);
        allRooms.add(room123);
        Room room124 = new Room(124);
        allRooms.add(room124);
        Room room125 = new Room(125);
        allRooms.add(room125);
        Room room126 = new Room(126);
        allRooms.add(room126);
        Room room127 = new Room(127);
        List<Capabilities> room127Capabilities = new ArrayList<>();
        room127Capabilities.add(Capabilities.Mental_Wellness);
        room127.setCapabilities(room127Capabilities);
        allRooms.add(room127);
        Room room128 = new Room(128);
        allRooms.add(room128);
        Room room129 = new Room(129);
        List<Capabilities> room129Capabilities = new ArrayList<>();
        room129Capabilities.add(Capabilities.Isolation);
        room129.setCapabilities(room129Capabilities);
        allRooms.add(room129);
    }

    // MODIFIES: allRooms
    // EFFECTS: initializes rooms 130-139
    public void initializeRooms130to139() {
        Room room130 = new Room(130);
        allRooms.add(room130);
        Room room131 = new Room(131);
        allRooms.add(room131);
        Room room132 = new Room(132);
        List<Capabilities> room132Capabilities = new ArrayList<>();
        room132Capabilities.add(Capabilities.Mental_Wellness);
        room132.setCapabilities(room132Capabilities);
        allRooms.add(room132);
        Room room133 = new Room(133);
        allRooms.add(room133);
        Room room134 = new Room(134);
        allRooms.add(room134);
        Room room135 = new Room(135);
        allRooms.add(room135);
        Room room136 = new Room(136);
        allRooms.add(room136);
        Room room137 = new Room(137);
        allRooms.add(room137);
        Room room138 = new Room(138);
        allRooms.add(room138);
        Room room139 = new Room(139);
        allRooms.add(room139);
    }

    // MODIFIES: allRooms
    // EFFECTS: initializes rooms 140-144
    public void initializeRooms140to144() {
        Room room140 = new Room(140);
        List<Capabilities> room140Capabilities = new ArrayList<>();
        room140Capabilities.add(Capabilities.Nitrous_Oxide);
        room140.setCapabilities(room140Capabilities);
        allRooms.add(room140);
        Room room141 = new Room(141);
        List<Capabilities> room141Capabilities = new ArrayList<>();
        room141Capabilities.add(Capabilities.Nitrous_Oxide);
        room141.setCapabilities(room141Capabilities);
        allRooms.add(room141);
        Room room142 = new Room(142);
        allRooms.add(room142);
        Room room143 = new Room(143);
        allRooms.add(room143);
        Room room144 = new Room(144);
        allRooms.add(room144);
    }

    // MODIFIES: allRooms
    // EFFECTS: initializes rooms 145-150
    public void initializeRooms145to150() {
        Room room145 = new Room(145);
        allRooms.add(room145);
        Room room146 = new Room(146);
        allRooms.add(room146);
        Room room147 = new Room(147);
        allRooms.add(room147);
        Room room148 = new Room(148);
        List<Capabilities> room148Capabilities = new ArrayList<>();
        room148Capabilities.add(Capabilities.Procedure);
        room148Capabilities.add(Capabilities.Nitrous_Oxide);
        room148.setCapabilities(room148Capabilities);
        allRooms.add(room148);
        Room room149 = new Room(149);
        List<Capabilities> room149Capabilities = new ArrayList<>();
        room149Capabilities.add(Capabilities.Procedure);
        room149Capabilities.add(Capabilities.Nitrous_Oxide);
        room149.setCapabilities(room149Capabilities);
        allRooms.add(room149);
        Room room150 = new Room(150);
        allRooms.add(room150);
    }

    // MODIFIES: allRooms
    // EFFECTS: initializes rooms 151-155
    public void initializeRooms151to155() {
        Room room151 = new Room(151);
        allRooms.add(room151);
        Room room152 = new Room(152);
        allRooms.add(room152);
        Room room153 = new Room(153);
        allRooms.add(room153);
        Room room154 = new Room(154);
        allRooms.add(room154);
        Room room155 = new Room(155);
        allRooms.add(room155);
    }
}