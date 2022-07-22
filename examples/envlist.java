///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.5.0
//JAVA_OPTIONS -Dmy_env_var=some_value

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "envlist", mixinStandardHelpOptions = true, version = "envlist 0.1",
        description = "envlist made with jbang")
class envlist implements Callable<Integer> {

    @Parameters(index = "0", description = "Regex to match environment variable or property names to output", defaultValue = ".*")
    private String regex;

    public static void main(String... args) {
        int exitCode = new CommandLine(new envlist()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("ENV:");
        System.getenv().entrySet().stream()
            .filter((t) -> t.getKey().matches(regex))
            .forEach(t -> System.out.println(t.getKey() + " = " + t.getValue()));

        System.out.println("PROPS:");
        System.getProperties().entrySet().stream()
            .filter((t) -> t.getKey().toString().matches(regex))
            .forEach(t -> System.out.println(t.getKey() + " = " + t.getValue()));

        return 0;
    }
}
