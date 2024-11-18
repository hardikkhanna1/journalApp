package net.engineeringdigest.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class WeatherEntity
{
        private Current current;

        @Getter
        @Setter
        public class Current{

                public int temperature;
                public int feelslike;
        }

}


