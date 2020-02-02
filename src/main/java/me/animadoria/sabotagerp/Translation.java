package me.animadoria.sabotagerp;

import java.util.HashMap;

public final class Translation {
    public static HashMap<SabotageLanguage, String> currentRoomTranslation = new HashMap<SabotageLanguage, String>() {{
        put(SabotageLanguage.PORTUGUESE, "Sala> Você está atualmente na sala: ");
        put(SabotageLanguage.ENGLISH, "Room> You are currently on room: ");
    }};

    public static HashMap<SabotageLanguage, String> switchedRoomTranslation = new HashMap<SabotageLanguage, String>() {{
        put(SabotageLanguage.PORTUGUESE, "Entrar> Você trocou de sala de ");
        put(SabotageLanguage.ENGLISH, "Join> You switched room from ");
    }};

    public static HashMap<SabotageLanguage, String> propositionTranslation = new HashMap<SabotageLanguage, String>() {{
        put(SabotageLanguage.PORTUGUESE, " para ");
        put(SabotageLanguage.ENGLISH, " to ");
    }};

    public static HashMap<SabotageLanguage, String> tabGameMessageStart = new HashMap<SabotageLanguage, String>() {{
        put(SabotageLanguage.PORTUGUESE, "Você está jogando ");
        put(SabotageLanguage.ENGLISH, "You are playing ");
    }};

    public static HashMap<SabotageLanguage, String> tabGameMessageEnd = new HashMap<SabotageLanguage, String>() {{
        put(SabotageLanguage.PORTUGUESE, " no sabotador.com");
        put(SabotageLanguage.ENGLISH, " on sabotador.com");
    }};

}
