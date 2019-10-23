package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.StationDAO;
import com.cy.chengyou.dtos.Station;
import com.cy.chengyou.services.StationService;
import com.cy.chengyou.utils.FileReadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDAO stationDAO;

    class StationFileRead extends FileReadUtil {

        @Override
        public void dealLine(int line, String context) {
            String[] text = context.split(",");
            Station station = new Station();
            station.setCode(text[2]);
            station.setName(text[1]);
            station.setPhonetic(text[3]);
            station.setFirstPhonetic(text[4]);
            try {
                stationDAO.insertStation(station);
            } catch (Exception e) {
                System.out.println(context);
            }
        }
    }


    @Override
    public void initStation() {
        StationFileRead stationFileRead = new StationFileRead();
        stationFileRead.readFileByLines("/work/station.txt");
    }
}
