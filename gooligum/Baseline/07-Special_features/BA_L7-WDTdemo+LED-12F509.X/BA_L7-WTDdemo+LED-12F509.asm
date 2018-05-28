;************************************************************************
;                                                                       *
;   Filename:      BA_L7-WDTdemo+LED-12F509.asm                 *
;   Date:          05/28/2018                                           *
;   File Version:  1.1                                                  *
;                                                                       *
;   Author:        Mike Rocha                                           *
;   Company:       RoCo, Inc.                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Architecture:  Baseline PIC                                         *
;   Processor:     12F508/509                                           *
;   NOTE:          WDT enable only works with the NON-DEBUG chip, that  *
;                  is, you must use the 12F509 8-pin package for this   *
;                  code to load and run properly.                       *
;                                                                       *
;                  When I tried with the AC162059 debug header, I got a *
;                  message:                                             *
;                  "The requested operation cannot continue with the    *
;                   following configurawtion bit setting(s):            *
;                   - Watchdog Timer Enable Bit = On                    *
;                   Please fix the settings(s) and try the operation    *
;                   again."                                             *
;                                                                       *
;                   Found the solution on Microchip forum page:         *
;                   Title: "I have a Watchdog problem"                  *
;                   URL: http://www.microchip.com/forums/m583878.aspx   *
;                   Comment: by purple_bobby Sunday, June 19, 2011 6:55 AM
;                            "Some debuggers cannot debug with the      *
;                             watchdog enabled (it is probably how the  *
;                             on chip software debugger works: using the*
;                             watch dog to interrupt after each         *
;                             instruction).                             *
;                                                                       *
;************************************************************************
;                                                                       *
;   Files required:                                                     *
;                                                                       *
;************************************************************************
;                                                                       *
;   Description:    Lesson 7, example 3b, p. 8b-9a                                  *
;                                                                       *
;   Demonstrates differentiation between WDT and POR reset              *
;                                                                       *
;   Turn on LED for 1 s, turn off, then enter endless loop              *
;   If enabled, WDT timer restarts after 2.3 s -> LED flashes           *
;   Turns on WDT LED to indicate WDT reset                              *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = flashing LED                                              *
;       GP2 = WDT-reset indicator LED                                   *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
	#include <stdmacros-base.inc> ; DbcneHi - debounce switch, wait for high
	                              ;    (requires TMR0 running at 256 uS/tick
				      
	EXTERN   delay10xW_R
				     
	radix    dec
				      
;***** CONFIGURATION ************************************************************
; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.
	#define  WATCHDOG
	
	IFDEF WATCHDOG
	    ; no ext reset, no code protect, no watchdog, int RC clock
	    __CONFIG   _MCLRE_ON & _CP_OFF & _WDT_ON & _IntRC_OSC
	ELSE
	    ; no ext reset, yes code protect, no watchdog, int RC clock
	    __CONFIG   _MCLRE_ON & _CP_ON & _WDT_OFF & _IntRC_OSC
	ENDIF
	    
;***** PIN ASSIGNMENTS **********************************************************
	constant REV='B'
	
	IF REV=='A'
	    #define  LED     GPIO,0       ; LED assigned to GP0
	    constant nLED=0
	ENDIF
	IF REV=='B'
	    #define  LED     GPIO,1       ; on/off indicator LED assigned to GP1
	    constant nLED=1               ; port bit 1
	    #define  WDT     GPIO,2       ; watchdog timer reset indicator on GP2
	    constant nWDT=2               ; port bit 2
	ENDIF
	
	#define BUTTON  GPIO,3            ; pushbutton assigned to GP3 (active low)
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration
	pagesel start
	goto    start             ; jump to main code
	
;***** Subroutine vectors
delay10xW                         ; delay W x 10 ms
        pagesel delay10xW_R
        goto    delay10xW_R       

;***** MAIN PROGRAM ************************************************************
MAIN    CODE
	
;***** Initialization
start	
        ; configure ports
	clrf    GPIO              ; start with all LEDs off
	movlw   ~(1<<nLED|1<<nWDT); configure LED pins as outputs
	tris    GPIO
	
	; cobfigure watchdog timer
	movlw   1<<PSA | b'111'   ; configure wake-up on change and Timer0
		; ----1---            prescaler assigned to Watchdog Timer (PSA = 1)
		; -----111            prescale = 128 (PS = 111)
	option                    ;   -> WDT period = 2.3 S

;***** Main code
main_loop
	; test for WDT-timeout reset
	btfss	STATUS,NOT_TO     ; if WDT timeout has occurred,
	bsf     WDT               ;    turn on WDT LED
	
	; flash LED
	bsf     LED               ; turn on "flash" LED
	DelayMS 1000              ; delay 1 S
	bcf     LED               ; turn off "flash" LED
	sleep                     ; /MCLR sets /TO on wake-up from sleep
	
	goto $                    ; wait forever

	END