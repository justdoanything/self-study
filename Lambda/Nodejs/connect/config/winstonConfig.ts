import { createLogger, format, transports } from 'winston';

const logger = createLogger({
    level: 'info',
    transports: [new transports.Console()],
    format: format.combine(
        format.printf(({ level, message }) => {
            return `[${level}]: ${message}`;
        }),
    ),
});

export default logger;
