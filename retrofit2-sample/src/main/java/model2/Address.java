package model2;

import retrofit.Retrofit;
import android.os.Parcelable;

@Retrofit
public abstract class Address implements Parcelable {
  public abstract double[] coordinates();
  public abstract String cityName();

  public static Address create(double[] coordinates, String cityName) {
      return builder().coordinates(coordinates).cityName(cityName).build();
  }

  public static Builder builder() {
      return new Retrofit_Address.Builder();
  }

  @Retrofit.Builder
  public interface Builder {
      public Builder coordinates(double[] x);
      public Builder cityName(String x);
      public Address build();
  }

  @Retrofit.Validate
  public void validate() {
      if (cityName().length() < 2) {
          throw new IllegalStateException("Not a city name");
      }
  }
}
