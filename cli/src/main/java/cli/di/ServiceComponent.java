package cli.di;

import cli.service.GrpcPingService;
import dagger.Component;

@Component(modules = {ServiceModule.class, GrpcModule.class, ConfigModule.class})
public interface ServiceComponent {
    GrpcPingService getPingService();
}
