package com.devstresk.devstreakapp.data.di;

import com.devstresk.devstreakapp.data.remote.CodeforcesApiService;
import com.devstresk.devstreakapp.domain.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class NetworkModule_ProvideUserRepositoryFactory implements Factory<UserRepository> {
  private final Provider<CodeforcesApiService> apiServiceProvider;

  public NetworkModule_ProvideUserRepositoryFactory(
      Provider<CodeforcesApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public UserRepository get() {
    return provideUserRepository(apiServiceProvider.get());
  }

  public static NetworkModule_ProvideUserRepositoryFactory create(
      Provider<CodeforcesApiService> apiServiceProvider) {
    return new NetworkModule_ProvideUserRepositoryFactory(apiServiceProvider);
  }

  public static UserRepository provideUserRepository(CodeforcesApiService apiService) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideUserRepository(apiService));
  }
}
