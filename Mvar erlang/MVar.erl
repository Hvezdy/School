-module(mvar).
-export([e_initMVar/0 Mvar/1])

e_initMVar() -> 
	Pid = spawn(fun() -> Mvar([])),
	register(mvar, Pid),
	Pid.
	
	
e_putMVar(X) ->
	mvar ! {put, self(), X},
	receive
		{ok} -> ok;
		{full} -> full
	end.

e_takeMVar() ->
	mvar ! {take, self()},
	receive
		empty -> empty;
		{ok, X} -> X
	end.
	

Mvar([]) ->
	receive
		{put, From, X} ->
			Mvar([X]),
			From ! ok;
		{take, From} ->
			From ! empty
	end.
	
Mvar([X]) ->
	receive
		{put,From,X} ->
			From ! full;
		{take,From} ->
			Mvar([]),
			From ! {ok, X}
	end.