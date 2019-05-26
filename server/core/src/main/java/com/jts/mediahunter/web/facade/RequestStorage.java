package com.jts.mediahunter.web.facade;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RequestStorage {

    /**
     * Key: EID of Channel
     * Value: List of all MCP provider names
     */
    private final static Map<String, List<String>> CHANNEL_STORAGE = new HashMap<>();

    public static void addToChannelStorage(String EID, String mcpName) {
        if (CHANNEL_STORAGE.containsKey(EID)) {
            CHANNEL_STORAGE.get(EID).add(mcpName);
            return;
        }
        List<String> newList = new ArrayList<>();
        newList.add(mcpName);
        CHANNEL_STORAGE.put(EID, newList);
    }

    public static void removeFromChannelStorage(String EID, String mcpName) {
        if (CHANNEL_STORAGE.containsKey(EID)) {
            CHANNEL_STORAGE.get(EID).remove(mcpName);
            if (CHANNEL_STORAGE.get(EID).size() == 0) {
                CHANNEL_STORAGE.remove(EID);
            }
        }
    }

    public static boolean isPresent(String EID, String mcpName){
        return CHANNEL_STORAGE.keySet().contains(EID) && CHANNEL_STORAGE.get(EID).contains(mcpName);
    }

}
