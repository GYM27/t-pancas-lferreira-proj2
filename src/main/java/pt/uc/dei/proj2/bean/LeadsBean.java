package pt.uc.dei.proj2.bean;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;

import pt.uc.dei.proj2.dto.LeadsDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

@ApplicationScoped
public class LeadsBean implements Serializable {

    private static final String FILENAME = "leads.json";
    private ArrayList<LeadsDto> leads;

    public LeadsBean() {

        File f = new File(FILENAME);

        if (f.exists()) {
            try {
                FileReader reader = new FileReader(f);
                leads = JsonbBuilder.create()
                        .fromJson(reader,
                                new ArrayList<LeadsDto>() {}.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            leads = new ArrayList<>();
        }
    }

    public ArrayList<LeadsDto> getLeads() {
        return leads;
    }

    public void addLead(LeadsDto dto) {

        dto.setId(generateId());
        dto.setDate(Instant.now().toString());

        leads.add(dto);
        writeIntoJsonFile();
    }

    public LeadsDto getLead(int id) {
        for (LeadsDto l : leads) {
            if (l.getId() == id)
                return l;
        }
        return null;
    }

    public boolean updateLead(int id, LeadsDto dto) {

        for (LeadsDto l : leads) {
            if (l.getId() == id) {
                l.setTitle(dto.getTitle());
                l.setDescription(dto.getDescription());
                l.setState(dto.getState());

                writeIntoJsonFile();
                return true;
            }
        }
        return false;
    }

    public boolean removeLead(int id) {

        for (LeadsDto l : leads) {
            if (l.getId() == id) {
                leads.remove(l);
                writeIntoJsonFile();
                return true;
            }
        }
        return false;
    }

    private void writeIntoJsonFile() {

        Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));

        try {
            jsonb.toJson(leads, new FileOutputStream(FILENAME));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private int generateId() {
        return leads.stream()
                .mapToInt(LeadsDto::getId)
                .max()
                .orElse(0) + 1;
    }
}
