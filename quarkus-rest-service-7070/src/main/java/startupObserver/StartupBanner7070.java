//package startupObserver;
//
//import banner.QuarkusRestService7070Banner;
//import io.quarkus.runtime.StartupEvent;
//import jakarta.enterprise.event.Observes;
//import jakarta.inject.Inject;
//import jakarta.inject.Singleton;
//
//@Singleton
//public class StartupBanner7070 {
//    @Inject
//    QuarkusRestService7070Banner bannerListener;
//
//    void onStart(@Observes StartupEvent event) {
//        bannerListener.displayBanner(event);
//    }
//}
