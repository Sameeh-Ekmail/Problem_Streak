package com.devstresk.devstreakapp.presentation;

import com.devstresk.devstreakapp.data.local.UserPreferences;
import com.devstresk.devstreakapp.domain.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class UserViewModel_Factory implements Factory<UserViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public UserViewModel_Factory(Provider<UserRepository> userRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public UserViewModel get() {
    return newInstance(userRepositoryProvider.get(), userPreferencesProvider.get());
  }

  public static UserViewModel_Factory create(Provider<UserRepository> userRepositoryProvider,
      Provider<UserPreferences> userPreferencesProvider) {
    return new UserViewModel_Factory(userRepositoryProvider, userPreferencesProvider);
  }

  public static UserViewModel newInstance(UserRepository userRepository,
      UserPreferences userPreferences) {
    return new UserViewModel(userRepository, userPreferences);
  }
}
