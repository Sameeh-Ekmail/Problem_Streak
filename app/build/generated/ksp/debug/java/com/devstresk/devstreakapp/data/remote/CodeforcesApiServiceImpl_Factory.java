package com.devstresk.devstreakapp.data.remote;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.ktor.client.HttpClient;
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
public final class CodeforcesApiServiceImpl_Factory implements Factory<CodeforcesApiServiceImpl> {
  private final Provider<HttpClient> clientProvider;

  public CodeforcesApiServiceImpl_Factory(Provider<HttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public CodeforcesApiServiceImpl get() {
    return newInstance(clientProvider.get());
  }

  public static CodeforcesApiServiceImpl_Factory create(Provider<HttpClient> clientProvider) {
    return new CodeforcesApiServiceImpl_Factory(clientProvider);
  }

  public static CodeforcesApiServiceImpl newInstance(HttpClient client) {
    return new CodeforcesApiServiceImpl(client);
  }
}
