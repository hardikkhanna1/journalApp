package net.engineeringdigest.journalApp.api.response;
import lombok.Getter;
import lombok.Setter;



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


