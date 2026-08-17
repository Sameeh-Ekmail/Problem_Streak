package com.devstresk.devstreakapp.data.di;

import com.devstresk.devstreakapp.data.remote.CodeforcesApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.ktor.client.HttpClient;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class NetworkModule_ProvideCodeforcesApiServiceFactory implements Factory<CodeforcesApiService> {
  private final Provider<HttpClient> httpClientProvider;

  public NetworkModule_ProvideCodeforcesApiServiceFactory(Provider<HttpClient> httpClientProvider) {
    this.httpClientProvider = httpClientProvider;
  }

  @Override
  public CodeforcesApiService get() {
    return provideCodeforcesApiService(httpClientProvider.get());
  }

  public static NetworkModule_ProvideCodeforcesApiServiceFactory create(
      Provider<HttpClient> httpClientProvider) {
    return new NetworkModule_ProvideCodeforcesApiServiceFactory(httpClientProvider);
  }

  public static CodeforcesApiService provideCodeforcesApiService(HttpClient httpClient) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideCodeforcesApiService(httpClient));
  }
}
