#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>
#define BUF_LEN_MAX 64
#define ARGV_MAX 10
#define HISTORY_MAX 10

int main()
{
	char *argv[ARGV_MAX];
	char *history[HISTORY_MAX];
	char *token;
	int argc = 0;
	int ifContinue = 1;
	int input_cnt = 0;

	while (ifContinue)
	{
		char input_str[BUF_LEN_MAX];
		printf("simpShell> ");
		if (!fgets(input_str, BUF_LEN_MAX, stdin))
		{
			printf("Invalid input, now exiting shell\n");
			break;
		}
		if (!strcmp(input_str, "exit\n"))
		{
			printf("Receive an exit\n");
			break;
		}
		if (!strcmp(input_str, "history\n"))
		{
			if (!input_cnt)
			{
				printf("Err: History is empty\n");
				continue;
			}
			printf("History:\n");
			int history_iter = 0;
			for (history_iter = 0; history_iter < 10 && history_iter < input_cnt; history_iter++)
			{
				printf("No.%d: ", history_iter + 1);
				puts(history[(history_iter + 1) % HISTORY_MAX]);
			}
			continue;
		}

		if (!strcmp(input_str, "\n"))
		{
			printf("Receive nothing\n");
			continue;
		}

		if (input_str[strlen(input_str) - 1] == '\n')
		{
			input_str[strlen(input_str) - 1] = '\0';
		}

		input_cnt++;
		history[input_cnt % HISTORY_MAX] = input_str;
		puts(history[input_cnt % HISTORY_MAX]);

		token = strtok(input_str, " ");
		int arg_cnt = 0;
		while (token)
		{
			argv[arg_cnt] = token;
			arg_cnt++;
			token = strtok(NULL, " ");
		}
		argc = arg_cnt - 1;
		argv[arg_cnt] = NULL;
		int iter_arg = 0;
		for (iter_arg = 0; iter_arg < arg_cnt; iter_arg++)
		{
			printf("Arg %d is ", iter_arg);
			puts(argv[iter_arg]);
		}
		int pid = fork();
		if (pid == 0)
		{
			printf("Child process started\n");
			int status = execvp(argv[0], argv);
			if (status == -1)
			{
				printf("Execution failed\n");
				return 1;
			}
		}
		else
		{
			wait(NULL);
			printf("Child process exited\n");
		}
	}
	return 0;
}
